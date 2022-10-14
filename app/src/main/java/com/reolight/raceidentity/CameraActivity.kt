package com.reolight.raceidentity


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.util.Log.ASSERT
import android.util.Log.WARN
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.*
import com.reolight.raceidentity.databinding.ActivityCameraBinding
import com.reolight.raceidentity.support.FaceParams
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.pow
import kotlin.math.sqrt

class CameraActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityCameraBinding
    private lateinit var camera: Camera
    private var faceParams: FaceParams? = null

    private class FaceAnalyzer(val binding: ActivityCameraBinding, var faceParams: FaceParams?) : ImageAnalysis.Analyzer {
        private var detector : FaceDetector

        init {
            val options = FaceDetectorOptions.Builder()
                .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
                .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                .build()
            detector = FaceDetection.getClient(options)
        }

        fun getDistance(point1: PointF, point2: PointF): Float =
            sqrt((point1.x - point2.x).pow(2) + (point1.y - point2.y).pow(2))

        private fun onSuccessfulRecognitionProcessFirstFace(face: Face?){
            face?.let { f ->
                faceParams = FaceParams().apply {
                    noseWidth = getDistance(
                        f.allContours[FaceContour.NOSE_BOTTOM].points.first(),
                        f.allContours[FaceContour.NOSE_BOTTOM].points.last()
                    )
                     noseHeight = getDistance(
                         f.allContours[FaceContour.NOSE_BRIDGE].points.first(),
                         f.allContours[FaceContour.NOSE_BRIDGE].points.last()
                    )
                    val skullHeight = f.boundingBox.height().toFloat()
                    height2 = getDistance(
                        f.allContours[FaceContour.FACE].points.first(),
                        f.allContours[FaceContour.FACE].points[f.allContours[FaceContour.FACE].points.size / 2]
                    )
                    height1 = getDistance(
                        f.allContours[FaceContour.FACE].points[f.allContours[FaceContour.FACE].points.size / 2],
                        PointF(
                            (f.allContours[FaceContour.LEFT_EYEBROW_BOTTOM].points.last().x +
                                    f.allContours[FaceContour.RIGHT_EYEBROW_BOTTOM].points.last().x) / 2,
                            (f.allContours[FaceContour.LEFT_EYEBROW_BOTTOM].points.last().y +
                                    f.allContours[FaceContour.RIGHT_EYEBROW_BOTTOM].points.last().y) / 2
                        )
                    )
                    faceWidth = getDistance(
                        f.allContours[FaceContour.FACE].points[ f.allContours[FaceContour.FACE].points.size / 4],
                        f.allContours[FaceContour.FACE].points[f.allContours[FaceContour.FACE].points.size - f.allContours[FaceContour.FACE].points.size / 4]
                    )

                    foreheadHeight = skullHeight - height1
                }

                Log.w("ANALISYS", faceParams.toString())

                with(binding.confirmButton) {
                    visibility = View.VISIBLE
                }
            }
        }

        @androidx.camera.core.ExperimentalGetImage
        override fun  analyze(imageProxy: ImageProxy) {
            if (binding.confirmButton.visibility == View.GONE){
                imageProxy.image?.let { img ->
                    val image = InputImage.fromMediaImage(img, imageProxy.imageInfo.rotationDegrees)
                    detector.process(image).addOnSuccessListener { faces ->
                        Log.w("ANAL", "ANALYSING")
                        onSuccessfulRecognitionProcessFirstFace(faces.firstOrNull())
                    }
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
                .also {
                    it.setAnalyzer(cameraExecutor, FaceAnalyzer(viewBinding, faceParams))
                }

            viewBinding.confirmButton.setOnClickListener(){
                val intent = Intent(this, ChartActivity::class.java)
                intent.putExtra(FaceParams::class.simpleName, faceParams)
                startActivity(intent)
                this.finish()
            }

            try{
                cameraProvider.unbindAll()
                camera = cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageAnalyze)
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
            ).toTypedArray()
    }
}