import input.MouseHandler;
import javafx.scene.shape.Sphere;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import physics.PointMass;
import physics.Spring;

import java.nio.*;
import java.util.Random;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFWVidMode.WIDTH;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main {

    // The window handle
    private long window;
    private static boolean something_selected = false;
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback   keyCallback;
    private GLFWCursorPosCallback mouseCallback;


    public void run() {


        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }


    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(1280, 960, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true);// We will detect this in the rendering loop

        });

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        //glfwSetKeyCallback(window, keyCallback = new KeyboardHandler());



        //  n b  glfwSetCursorPosCallback(window,mouseCallback=new MouseHandler());

        // Get the resolution of the primary monitor


        // Center our window
//        glfwSetWindowPos(
//                window,
//                (GLFWVidMode.width(vidmode) - 1280) / 2,
//                (GLFWVidMode.height(vidmode) - 960) / 2
//        );

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }


    private void renderSphere(float x, float y, float z) {
        glColor3f(1f,1f,0f);
        glPointSize( 10.0f );
        glBegin( GL_POINTS);
        glVertex3f(0f,0f,0f);
        glEnd();
        glBegin( GL_LINES);
        glVertex3f(0f,0f,0f);
        glVertex3f(x,y,z);  // l= 80

        glEnd();

        glColor3f(0.6f,0.6f,0.6f);
        glBegin(GL_QUADS);

        glVertex3f(x-0.05f,y-0.05f, z-0.05f);
        glVertex3f(x+0.05f,y-0.05f, z-0.05f);
        glVertex3f(x+0.05f,y+0.05f, z-0.05f);
        glVertex3f(x-0.05f,y+0.05f, z-0.05f);
        glScalef(0.1f,0.1f,0.1f);

        glEnd();
    }

        ///glPopMatrix();



    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
        Spring a =new Spring();




        // Set the clear color
       glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
//        glMatrixMode(GL_PROJECTION);
//        glOrtho(0, 640, 480, 0, 1, -1);
//        glMatrixMode(GL_MODELVIEW);
//        MouseHandler mousePointer = new MouseHandler();
//        glfwSetCursorPosCallback(window,mousePointer);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
//        float a = 2f;
//        float b = 0.002f;
//        float c = 0.00001f;
//        float d = 0.0002f;
//        float t= -1f;

        Spring spring = new Spring();
        PointMass mass =new PointMass();

        float t=0f;
        float dt = 0.0001f;



        while ( !glfwWindowShouldClose(window) ) {
           glClear(GL_COLOR_BUFFER_BIT| GL_DEPTH_BUFFER_BIT); // clear the framebuffer

           // glBegin(GL_LINES);
            //renderSphere(0.5f, -0.5f, 0f);
            //renderSphere(0.5f, 0.5f, 0f);
            //renderSphere(-0.5f, 0.5f, 1f);

            //float x = (float) mousePointer.getXpos();
            //float y = (float) mousePointer.getYpos();
            //System.out.println(x);
//            x=  (x-1280f*0.5f)/1280f;
//            y= -(y-960f*0.5f)/960;
            spring.giveForce(mass);
            mass.update(dt);
            //System.out.println(mass.force.x);
            renderSphere(mass.position.x,mass.position.y, mass.position.z);


//            renderSphere(1f, -0.5f, -4f);
//            renderSphere(2f, -0.5f, -5f);
//            glVertex3f(0.5f,  0.0f, 0.0f);
//            glVertex3f( 0.0f,  0.5f,  0.0f);
//            glVertex3f(0.0f, -0.5f,  0.0f);
//            //glVertex3f(-0.5f, -0.0f,  0.0f);
            //glEnd();
            //glEnd();

            glfwSwapBuffers(window); // swap the color buffers
            // update the Display and read events
            //Display.update();
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }

}