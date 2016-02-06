package org.usfirst.frc1982.Robot2014new.commands;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import edu.wpi.first.wpilibj.CameraServer;

public class CameraFeeds
{
	private final int cam0;
	private final int cam1;
	private int curCam;
	private Image frame;
	private CameraServer server;
	
	public CameraFeeds()
	{
        // Get camera ids by supplying camera name ex 'cam0', found on roborio web interface
        cam0 = NIVision.IMAQdxOpenCamera(Config.CameraFeeds.camName0, NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        cam1 = NIVision.IMAQdxOpenCamera(Config.CameraFeeds.camName1, NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        curCam = cam0;
        // Img that will contain camera img
        frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
        // Server that we'll give the img to
        server = CameraServer.getInstance();
        server.setQuality(Config.CameraFeeds.imgQuality);
	}
	
	public void init()
	{
		changeCam(cam0);
	}
	
	public void SwitchTo(int cam) {
		if (cam == 0) {
			changeCam(cam0);
		} else if (cam == 1) {
			changeCam(cam1);
		}
	}
	
	public void run() {
		updateCam();
	}
	
	/**
	 * Stop aka close camera stream
	 */
	public void end()
	{
		NIVision.IMAQdxStopAcquisition(curCam);
	}
	
	/**
	 * Change the camera to get imgs from to a different one
	 * @param newId for camera
	 */
	public void changeCam(int newId)
    {
//		NIVision.IMAQdxStopAcquisition(curCam);
		NIVision.IMAQdxStartAcquisition(newId);
    	NIVision.IMAQdxConfigureGrab(newId);
//    	NIVision.IMAQdxStartAcquisition(newId);
    	curCam = newId;
    }
    
	/**
	 * Get the img from current camera and give it to the server
	 */
    public void updateCam()
    {
    	NIVision.IMAQdxGrab(curCam, frame, 1);
        server.setImage(frame);
    }
}