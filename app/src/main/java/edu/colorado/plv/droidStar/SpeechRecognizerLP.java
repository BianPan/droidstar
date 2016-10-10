package edu.colorado.plv.droidStar;

import android.os.Bundle;
import android.os.Handler.Callback;
import android.content.Intent;
import android.content.Context;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import static edu.colorado.plv.droidStar.Static.*;

public class SpeechRecognizerLP {

    private SpeechRecognizer sr;
    private Context context;

    private static Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        .putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                  RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        .putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                  "edu.colorado.plv.droidStar")
        .putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5)
        .putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

    public static String START = "start";
    public static String STOP = "stop";
    public static String CANCEL = "cancel";

    private static void logl(String m) {
        log("PURPOSE", m);
    }


    public static boolean isError(String output) {
        return false; //TODO
    }

    SpeechRecognizerLP(Context c) {
        this.context = c;
        this.reset();
    }

    public void reset() {
        this.sr = SpeechRecognizer.createSpeechRecognizer(this.context);
        logl("LP has been reset.");
    }

    public void giveInput(Callback forOutput, String input) {
        logl("LP received input \"" + input + "\"...");
        sr.setRecognitionListener(new Listener(forOutput));
        handleInput(input);
    }

    public void handleInput(String i) {
        if (i.equals(START)) {
            logl("Invoking \"startListening()\"...");
            sr.startListening(intent);
        } else if (i.equals(STOP)) {
            logl("Invoking \"stopListening()\"...");            
            sr.stopListening();
        } else if (i.equals(CANCEL)) {
            logl("Invoking \"cancel()\"...");            
            sr.cancel();
        } else {
            logl("Unrecognized input received, doing nothing...");
        }
    }

    public class Listener implements RecognitionListener {

        private Callback forOutput;

        private void logcb(String callbackName) {
            logl("CALLBACK: " + callbackName);
        }


        Listener(Callback c) {
            super();
            this.forOutput = c;
        }

        private void respond(String output) {
            // Message message = new Message();
            // Bundle o = new Bundle();
            // o.putString("output", output);
            // message.setData(o);
            forOutput.handleMessage(quickMessage(output));
        }

        public void onReadyForSpeech(Bundle params) {
            logcb("onReadyForSpeech");
            respond("onReadyForSpeech");
        }

        public void onBeginningOfSpeech() {
            logcb("onBeginningOfSpeech");
            respond("onBeginningOfSpeech");
        }

        public void onEndOfSpeech() {
            logcb("onEndOfSpeech");
            respond("onEndOfSpeech"); 
        }

        public void onError(int error) {
            logcb("onError " + error);
            respond("onError " + error);
        }

        public void onResults(Bundle results) {
            logcb("results!");
            respond("results!");
        }

        public void onPartialResults(Bundle partialResults) {
            logcb("some results...");
            respond("some results...");
        }

        public void onEvent(int eventType, Bundle params) {
            logcb("event?");
            respond("event?");
        }

        public void onRmsChanged(float rmsdB) {
            // Too noisy!
            // logcb("seems the rms has changed.");
        }

        public void onBufferReceived(byte[] buffer) {
            logcb("buff aquired");
            respond("buff aquired");
        }

    }
}
