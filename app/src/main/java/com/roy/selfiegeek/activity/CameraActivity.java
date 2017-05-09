package com.roy.selfiegeek.activity;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.roy.selfiegeek.R;
import com.roy.selfiegeek.constants.Constants;
import com.roy.selfiegeek.coreui.CameraPreview;
import com.roy.selfiegeek.util.APISupportUtil;
import com.roy.selfiegeek.util.CameraUtil;
import com.roy.selfiegeek.util.FileUtil;
import com.roy.selfiegeek.util.UploadUtil;
import com.roy.selfiegeek.util.ViewUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author prabhat.roy.
 */

public class CameraActivity extends AppCompatActivity {

    private Camera mCamera;
    private CameraPreview mPreview;
    private MediaRecorder mMediaRecorder;
    private Button mVideoCaptureButton;
    private Button mImageCaptureButton;
    private String mVideoFile;
    private String mImageFile;
    private boolean isRecording = false;
    public static final String TAG = CameraActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        initPermissions();
    }

    private void initPermissions() {
        if (!APISupportUtil.hasPermissions(this, Constants.PERMISSIONS)) {
            APISupportUtil.getPermission(this, Constants.PERMISSIONS, Constants.PERMISSION_ALL);
        } else initViews();
    }

    /**
     * This method takes care of setting up of views like adding click listeners etc. to views
     */
    private void initViews() {
        // Create an instance of Camera
        mCamera = CameraUtil.getFrontCameraInstance();
        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        setUpCameraClickListener();
        setUpVideoClickListener();
    }

    /**
     * This method sets up click listener for the button that helps to take a video
     */
    private void setUpVideoClickListener() {
        // Add a listener to the Capture button
        mVideoCaptureButton = (Button) findViewById(R.id.button_video_capture);
        mVideoCaptureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isRecording) {
                            // stop recording and release camera
                            mMediaRecorder.stop();
                            // release the MediaRecorder object
                            CameraUtil.releaseMediaRecorder(mMediaRecorder);
                            // inform the user that recording has stopped
                            ViewUtil.setButtonText(mVideoCaptureButton, getString(R.string.capture));
                            isRecording = false;
                            // inform media scanner that to update the cursor listso that all gallery apps
                            // show the proper list of videos
                            FileUtil.galleryAddItem(CameraActivity.this, mVideoFile);
                            // Upload the video to dropbox
                            UploadUtil.uploadFile(CameraActivity.this, mVideoFile, getString(R.string.success_message_video));
                            // set the value as null for further usages
                            mVideoFile = null;
                        } else {
                            // initialize video camera
                            mMediaRecorder = new MediaRecorder();
                            mVideoFile = FileUtil.getOutputMediaFile(Constants.MEDIA_TYPE_VIDEO).toString();
                            boolean isVideoRecorderPrepared =
                                    CameraUtil.prepareVideoRecorder(mMediaRecorder, mCamera, mVideoFile, mPreview);
                            if (isVideoRecorderPrepared) {
                                // Camera is available and unlocked, MediaRecorder is prepared,
                                // now you can start recording
                                mMediaRecorder.start();
                                // inform the user that recording has started
                                ViewUtil.setButtonText(mVideoCaptureButton, getString(R.string.stop));
                                isRecording = true;
                            } else {
                                // prepare didn't work, release the camera
                                CameraUtil.releaseMediaRecorder(mMediaRecorder);
                            }
                        }
                    }
                }
        );
    }

    /**
     * This method sets up click listener for the button that helps to take a Picture
     */
    private void setUpCameraClickListener() {
        // Add a listener to the Capture button
        mImageCaptureButton = (Button) findViewById(R.id.button_image_capture);
        mImageCaptureButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // get an image from the camera
                        mCamera.startPreview();
                        mCamera.takePicture(null, null, mPicture);
                    }
                }
        );
    }


    // this is the callback once method initialization to be called once picture had been clicked
    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            mImageFile = FileUtil.getOutputMediaFile(Constants.MEDIA_TYPE_IMAGE).toString();
            if (mImageFile == null) {
                Log.d(TAG, "Error creating media file, check storage permissions: ");
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(mImageFile);
                fos.write(data);
                fos.close();
                FileUtil.galleryAddItem(CameraActivity.this, mImageFile);
                UploadUtil.uploadFile(CameraActivity.this, mImageFile, getString(R.string.success_message_image));
                mCamera.startPreview();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }
        }
    };

    /**
     * This is a lifecycle method of the activity which is called once the activity is not in the foreground
     * This helps in releasing media recorder and camera instance
     */
    @Override
    protected void onPause() {
        super.onPause();
        CameraUtil.releaseMediaRecorder(mMediaRecorder);       // if you are using MediaRecorder, release it first
        CameraUtil.releaseCamera(mCamera);              // release the camera immediately on pause event
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSION_ALL: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    initViews();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.i(TAG, "please give necessary permissions to make the app work");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
