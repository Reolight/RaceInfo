package com.reolight.raceidentity


import android.Manifest
import android.content.pm.PackageManager
import android.graphics.*
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.core.Camera
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.component1
import androidx.core.graphics.minus
import androidx.core.graphics.toRectF
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.*
import com.reolight.raceidentity.databinding.ActivityCameraBinding
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.pow
import kotlin.math.sqrt


typealias LumaListener = (luma: Double) -> Unit

class CameraActivity : AppCompatActivity() {
    private lateinit var imageCapture: ImageCapture
    private lateinit var viewBinding: ActivityCameraBinding
    private lateinit var camera: Camera

    private class Analyzer : ImageAnalysis.Analyzer {
        private var detector : FaceDetector
        init {
            val options = FaceDetectorOptions.Builder()
                .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                .build()
            detector = FaceDetection.getClient(options)
        }

        fun getDistance(point1: PointF, point2: PointF): Float =
            sqrt((point1.x - point2.x).pow(2) + (point1.y - point2.y).pow(2))

        private fun onSuccessfulRecognitionProcessFirstFace(face: Face?){
            face?.run {
                val noseWidth = getDistance(
                    allContours[FaceContour.NOSE_BOTTOM].points.first(),
                    allContours[FaceContour.NOSE_BOTTOM].points.last()
                )
                val noseHeight = getDistance(
                    allContours[FaceContour.NOSE_BRIDGE].points.first(),
                    allContours[FaceContour.NOSE_BRIDGE].points.last()
                )
                val skullHeight = face.boundingBox.height()
                val height2 = getDistance(
                    allContours[FaceContour.FACE].points.first(),
                    allContours[FaceContour.FACE].points[18]
                )
                val height1 = getDistance(
                    allContours[FaceContour.FACE].points[18],
                    PointF(
                        (allContours[FaceContour.LEFT_EYEBROW_BOTTOM].points.last().x +
                                allContours[FaceContour.RIGHT_EYEBROW_BOTTOM].points.last().x) / 2,
                        (allContours[FaceContour.LEFT_EYEBROW_BOTTOM].points.last().y +
                                allContours[FaceContour.RIGHT_EYEBROW_BOTTOM].points.last().y) / 2)
                    )
                val faceWidth = getDistance(
                    allContours[FaceContour.FACE].points[8],
                    allContours[FaceContour.FACE].points[28]
                )

                val foreheadH = skullHeight - height1
            }
        }

        @androidx.camera.core.ExperimentalGetImage
        override fun  analyze(imageProxy: ImageProxy) {
            imageProxy.image?.let { img ->
                val image = InputImage.fromMediaImage(img, imageProxy.imageInfo.rotationDegrees)
                detector.process(image).addOnSuccessListener { faces ->
                    onSuccessfulRecognitionProcessFirstFace(faces.first())
                }
            }
        }
    }

    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        if (allPermissionsGranted()){
            startCamera()
        } else{
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun startCamera(){
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewBinding.previewView.surfaceProvider)
                }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val imageAnalyze = ImageAnalysis.Builder()
                .build()

            try{
                cameraProvider.unbindAll()
                camera = cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture, imageAnalyze)
            } catch (exc: Exception){
                Log.e(TAG, "Юз кейз биндинг файлед", exc)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS){
            if (allPermissionsGranted()){
                startCamera()
            } else {
                Toast.makeText(
                    this,
                    "Пермиссии не грантированы юзверем",
                Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object{
        private const val TAG = "Race app"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P){
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}