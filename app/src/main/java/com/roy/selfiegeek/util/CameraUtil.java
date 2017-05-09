package com.roy.selfiegeek.util;

import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.util.Log;

import com.roy.selfiegeek.coreui.CameraPreview;

import java.io.IOException;

/**
 * @author prabhat.roy.
 */

public class CameraUtil {

    public static final String TAG = CameraUtil.class.getSimpleName();

    /**
     * This method is a safe way to get an instance of the front camera
     */
    public static Camera getFrontCameraInstance() {
        int cameraCount = 0;
        Camera cam = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            if (camIdx == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                try {
                    cam = Camera.open(camIdx);
                } catch (RuntimeException e) {
                }
            }
        }
        return cam;
    }

    /**
     * This method helps to release the camera instance if the activity is destroyed or goes to background
     * @param camera The camera object to be released
     */
    public static void releaseCamera(Camera camera) {
        if (camera != null) {
            camera.release();        // release the camera for other applications
            camera = null;
        }
    }

    /**
     * This method is to setup the video recorder to capture videos
     * @param mediaRecorder the object to be prepared for capturing videos
     * @param camera The camera object to be attached to the media recorder
     * @param videoFile thi file name in memory where the video is to be stored
     * @param preview the camera view to which the media recorder is to be attached
     * @return a boolean indicating whether the media recording instance have been prepared properly or not
     */
    public static boolean prepareVideoRecorder(MediaRecorder mediaRecorder, Camera camera, String videoFile, CameraPreview preview) {
        camera = CameraUtil.getFrontCameraInstance();
        // Step 1: Unlock and set camera to MediaRecorder
        camera.unlock();
        mediaRecorder.setCamera(camera);
        // Step 2: Set sources
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        // Step 3: Set a CamcorderProfile (requires API Level 8 or higher)
        mediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_LOW));
        // Step 4: Set output file

        mediaRecorder.setOutputFile(videoFile);
        // Step 5: Set the preview output
        mediaRecorder.setPreviewDisplay(preview.getHolder().getSurface());
        // Step 6: Prepare configured MediaRecorder
        try {
            mediaRecorder.prepare();
        } catch (IllegalStateException e) {
            Log.d(TAG, "IllegalStateException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder(mediaRecorder);
            return false;
        } catch (IOException e) {
            Log.d(TAG, "IOException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder(mediaRecorder);
            return false;
        }
        return true;
    }

    /**
     * Thi method helps in releasing media recorder instance if the activity is killed or goes out of foreground
     * @param mMediaRecorder The instance of media recorder to be released
     */
    public static void releaseMediaRecorder(MediaRecorder mMediaRecorder) {
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();   // clear recorder configuration
            mMediaRecorder.release(); // release the recorder object
            mMediaRecorder = null;
        }
    }


}
