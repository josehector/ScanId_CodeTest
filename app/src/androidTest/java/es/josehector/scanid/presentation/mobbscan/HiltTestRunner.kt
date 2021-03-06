package es.josehector.scanid.presentation.mobbscan

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.karumi.shot.ShotTestRunner
import dagger.hilt.android.testing.HiltTestApplication

class HiltTestRunner : ShotTestRunner() {

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }

}
