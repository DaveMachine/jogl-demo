(ns jogl-demo.demo
  (:import [java.awt BorderLayout Frame]
           [java.awt.event WindowEvent WindowAdapter]
           [javax.swing JFrame]
           [javax.media.opengl GLCapabilities GL GL2 GLEventListener GLProfile]
           [javax.media.opengl.glu GLU]
           [javax.media.opengl.awt GLCanvas GLJPanel]))

(defn setup-triangle
  [gl2 w h]
  (doto gl2
    (.glMatrixMode GL2/GL_PROJECTION)
    (.glLoadIdentity))
  ;; coordinate system origin at lower left with width and height same as the window
  (doto (GLU.)
    (.gluOrtho2D (float 0.0) (float w) (float 0.0) (float h)))
  (doto gl2
    (.glMatrixMode GL2/GL_MODELVIEW)
    (.glLoadIdentity)
    (.glViewport 0 0 w h)))

(defn render-triangle
  "Draw a triangle filling the window"
  [gl2 w h]
  (doto gl2
    (.glClear GL/GL_COLOR_BUFFER_BIT)
    (.glLoadIdentity)
    (.glBegin GL2/GL_TRIANGLES)
    (.glColor3f 1.0 0.0 0.0)
    (.glVertex2f 0.0 0.0)
    (.glColor3f 0.0 1.0 0.0)
    (.glVertex2f (float w) 0.0)
    (.glColor3f 0.0 0.0 1.0)
    (.glVertex2f (/ w 2.0) (float h))
    (.glEnd)))

(defn make-gl-event-listener
  []
  (proxy [GLEventListener] []
    (reshape [glautodrawable x y w h]
      (setup-triangle (.. glautodrawable getGL getGL2) w h))
    (init [glautodrawable])
    (dispose [glautodrawable])
    (display [glautodrawable]
      (render-triangle (.. glautodrawable getGL getGL2) (. glautodrawable getWidth) (. glautodrawable getHeight)))
    ))

(defn main-awt
  "A minimal program that draws with JOGL in an AWT Frame.
Author: Wade Walker"
  []
  (let [glprofile (GLProfile/getDefault)
        glcapabilities (GLCapabilities. glprofile)
        glcanvas (GLCanvas. glcapabilities)
        frame (Frame. "One Triangle AWT")]
    (doto glcanvas
      (.addGLEventListener (make-gl-event-listener)))
    (doto frame
      (.add glcanvas)
      (.addWindowListener (proxy [WindowAdapter] []
                            (windowClosing [windowevent]
                              (doto frame
                                (.remove glcanvas)
                                (.dispose)))))
      (.setSize 640 480)
      (.setVisible true))
    ))
;;(main-awt)

(defn main-swing
  "A minimal program that draws with JOGL in a Swing JFrame using the AWT GLCanvas.
Author: Wade Walker"
  []
  (let [glprofile (GLProfile/getDefault)
        glcapabilities (GLCapabilities. glprofile)
        glcanvas (GLCanvas. glcapabilities)
        jframe (JFrame. "One Triangle Swing GLCanvas")]
    (doto glcanvas
      (.addGLEventListener (make-gl-event-listener)))
    (.. jframe getContentPane (add glcanvas BorderLayout/CENTER))
    (doto jframe
      (.setDefaultCloseOperation JFrame/DISPOSE_ON_CLOSE)
      (.setSize 640 480)
      (.setVisible true))
    ))
;;(main-swing)

(defn main-swing-gljpanel
  "A minimal program that draws with JOGL in a Swing JFrame using the AWT GLJPanel.
Author: Wade Walker"
  []
  (let [glprofile (GLProfile/getDefault)
        glcapabilities (GLCapabilities. glprofile)
        gljpanel (GLJPanel. glcapabilities)
        jframe (JFrame. "One Triangle Swing GLJPanel")]
    (doto gljpanel
      (.addGLEventListener (make-gl-event-listener)))
    (.. jframe getContentPane (add gljpanel BorderLayout/CENTER))
    (doto jframe
      (.setDefaultCloseOperation JFrame/DISPOSE_ON_CLOSE)
      (.setSize 640 480)
      (.setVisible true))
    ))
;;(main-swing-gljpanel)
