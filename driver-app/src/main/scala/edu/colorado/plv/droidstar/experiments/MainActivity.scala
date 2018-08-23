package edu.colorado.plv.droidstar
package experiments

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import scala.collection.JavaConverters._

import android.os.Environment

class MainActivity extends AppCompatActivity {

  // List learning purposes you wish to learn here.  They will be
  // performed in the order they are listed.
  lazy val experiments = Seq[LearningPurpose](
    new lp.CountDownTimerLP(this),
    new lp.FileObserverLP(this),
    new lp.VelocityTrackerLP(this),
    new lp.SpellCheckerSessionLP(this),
    new lp.SpeechRecognizerLP(this)
      // etc.
  )


  override def onCreate(s: Bundle): Unit = {
    super.onCreate(s)
    weCanWrite() match {
      case true => experiment(this, experiments.asJava)
      case false => println("Seems we can't report results...")
    }
  }

  def weCanWrite(): Boolean =
    Environment
      .getExternalStorageState()
      .equals(Environment.MEDIA_MOUNTED)
}
