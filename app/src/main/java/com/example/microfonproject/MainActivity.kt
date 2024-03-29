package com.example.microfonproject

import android.Manifest
import android.Manifest.permission.RECORD_AUDIO
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import be.tarsos.dsp.io.android.AudioDispatcherFactory
import be.tarsos.dsp.AudioEvent
import be.tarsos.dsp.pitch.PitchDetectionResult
import be.tarsos.dsp.pitch.PitchDetectionHandler
import be.tarsos.dsp.pitch.PitchProcessor.PitchEstimationAlgorithm
import be.tarsos.dsp.pitch.PitchProcessor
import be.tarsos.dsp.AudioProcessor
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.content.PermissionChecker.checkCallingOrSelfPermission
import java.security.AccessController.getContext


class MainActivity : AppCompatActivity() {

    var dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


            val pdh = PitchDetectionHandler { res, e ->
                val pitchInHz = res.pitch
                runOnUiThread { processPitch(pitchInHz) }
            }

            val pitchProcessor = PitchProcessor(PitchEstimationAlgorithm.FFT_YIN, 22050f, 1024, pdh)
            dispatcher.addAudioProcessor(pitchProcessor)

            val audioThread = Thread(dispatcher, "Audio Thread")
            audioThread.start()

    }



    fun processPitch(pitchInHz: Float) {

        pitchText.setText("" + pitchInHz)

        if (pitchInHz >= 110 && pitchInHz < 123.47) {
            //A
            noteText.setText("A")
        } else if (pitchInHz >= 123.47 && pitchInHz < 130.81) {
            //B
            noteText.setText("B")
        } else if (pitchInHz >= 130.81 && pitchInHz < 146.83) {
            //C
            noteText.setText("C")
        } else if (pitchInHz >= 146.83 && pitchInHz < 164.81) {
            //D
            noteText.setText("D")
        } else if (pitchInHz >= 164.81 && pitchInHz <= 174.61) {
            //E
            noteText.setText("E")
        } else if (pitchInHz >= 174.61 && pitchInHz < 185) {
            //F
            noteText.setText("F")
        } else if (pitchInHz >= 185 && pitchInHz < 196) {
            //G
            noteText.setText("G")
        }
    }


}
