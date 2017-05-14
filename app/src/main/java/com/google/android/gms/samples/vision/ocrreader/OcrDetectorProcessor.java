/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.android.gms.samples.vision.ocrreader;

import android.speech.tts.TextToSpeech;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.samples.vision.ocrreader.ui.camera.GraphicOverlay;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;

import java.util.Objects;

import static java.lang.Integer.getInteger;
import static java.lang.Integer.valueOf;



/**
 * A very simple Processor which gets detected TextBlocks and adds them to the overlay
 * as OcrGraphics.
 */
public class OcrDetectorProcessor implements Detector.Processor<TextBlock> {

    private GraphicOverlay<OcrGraphic> mGraphicOverlay;
    private TextToSpeech tts;

    OcrDetectorProcessor(GraphicOverlay<OcrGraphic> ocrGraphicOverlay) {
        mGraphicOverlay = ocrGraphicOverlay;
    }

    /**
     * Called by the detector to deliver detection results.
     * If your application called for it, this could be a place to check for
     * equivalent detections by tracking TextBlocks that are similar in location and content from
     * previous frames, or reduce noise by eliminating TextBlocks that have not persisted through
     * multiple detections.
     */
    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        mGraphicOverlay.clear();
        SparseArray<TextBlock> items = detections.getDetectedItems();
        for (int i = 0; i < items.size(); ++i) {
            TextBlock item = items.valueAt(i);
            if (item != null && item.getValue() != null) {
                // Log.d("OcrDetectorProcessor", "Text detected! " + item.getValue());
                if (Objects.equals(item.getValue(), "12549")) {
                   Snackbar.make(mGraphicOverlay, "Found Part 12549",
                            Snackbar.LENGTH_LONG)
                            .show();
                    /* OcrGraphic graphic = new OcrGraphic(mGraphicOverlay, item);
                    mGraphicOverlay.add(graphic); */

                }
            }
            /**

            OcrGraphic graphic = new OcrGraphic(mGraphicOverlay, item);
            mGraphicOverlay.add(graphic);
            */


            String myitem;
            myitem = "";
            try {
                myitem = item.getValue();
                myitem = myitem.trim();
            } catch (NullPointerException e) {

                Log.d("OcrDetectorProcessor", "Error getting item value " + item.getValue());
            }

            if (isNumeric(myitem)) {
                // tts.speak(myitem, TextToSpeech.QUEUE_ADD, null, "DEFAULT");

            }

            try {

                Integer thispart;
                thispart = Integer.parseInt(myitem);
                if (thispart > 9999 && thispart < 99999) {
                    OcrGraphic graphic = new OcrGraphic(mGraphicOverlay, item);
                    mGraphicOverlay.add(graphic);
                    Boolean pause;
                    pause = true;

                    Log.d("OcrDetectorProcessor", "Part number " + item.getValue());
                    // tts.speak(myitem, TextToSpeech.QUEUE_ADD, null, "DEFAULT");
                }
            } catch (Exception e) {
                Log.d("OcrDetectorProcessor", "Not a part number " + item.getValue());
            }

        }
    }

    /**
     * Frees the resources associated with this detection processor.
     */
    @Override
    public void release() {
        mGraphicOverlay.clear();
    }

    public static boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
}
