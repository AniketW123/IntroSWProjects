from xml.dom.minidom import DOMImplementation
import cv2
import glob

from cv2 import CALIB_CB_NORMALIZE_IMAGE

class 2dCoordFinder:
    image, retval, corners, patternsize = None
    def __init__(self, imagepath, dimensions, winsize):
        self.imagepath = imagepath
        self.dimensions = dimensions
        self.winsize = winsize
        image = glob.glob(imagepath)
        patternsize = cv2.LDR_SIZE()
        flags = cv2.CALIB_CB_ADAPTIVE_THRESH + CALIB_CB_NORMALIZE_IMAGE
        retval, corners = cv2.findChessboardCorners(image, patternsize, flags)
        cv2.cornerSubPix(image, corners, winsize, (-1,-1), cv2.TermCriteria_MAX_ITER)