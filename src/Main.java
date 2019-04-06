import com.sun.javafx.geom.Vec3f;
import input.MouseHandler;
import javafx.scene.shape.Sphere;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import physics.PointMass;
import physics.SpringMass;

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


    private void renderSphere(float x0, float y0, float z0, float x, float y, float z) {


        glColor3f(1f,1f,0f);
        glPointSize( 10.0f );
        glBegin( GL_POINTS);
        glVertex3f(x0,y0,z0);
        glEnd();

//        glScalef(1f,0.1f,0.1f);
        glBegin( GL_LINES);
        glVertex3f(x0,y0,z0);
        glVertex3f(x,y,z);  // l= 80

        glEnd();

        glColor3f(0.6f,0.6f,0.3f);
        glBegin(GL_QUADS);

        glVertex3f(x-0.025f,y-0.025f, z-0.025f);
        glVertex3f(x+0.025f,y-0.025f, z-0.025f);
        glVertex3f(x+0.025f,y+0.025f, z-0.025f);
        glVertex3f(x-0.025f,y+0.025f, z-0.025f);
//        glScalef(0.01f,0.01f,0.01f);

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




        PointMass fixed = new PointMass();
        fixed.setPosition(new Vec3f(-.5f,.75f,0f));

        fixed.setMass(Float.POSITIVE_INFINITY);
//        PointMass mass0 =new PointMass();
        PointMass fixed2 = new PointMass();
        fixed2.setMass(Float.POSITIVE_INFINITY);
        fixed2.setPosition(new Vec3f(.0f,.75f,0f));
//        mass0.setPosition(new Vec3f(0.2f,0f,0f));
//        PointMass mass1 = new PointMass();
//        mass1.setPosition(new Vec3f(0.4f,0f,0f));
//        PointMass mass2 = new PointMass();
//        mass2.setPosition(new Vec3f(0.6f,0f,0f));
//
        PointMass[] mass= new PointMass[16];

        int n= mass.length;

        for (int i=0;i<16;i++){
            mass[i]= new PointMass();

            float ypos= 0.50f-(float)(i/4) /10f;
            float xpos = -0.5f + (float)(i%4)/5f;

            mass[i].setPosition(new Vec3f(xpos, ypos,0f));
//            mass[i].setPosition(new Vec3f( i%4, i/4,0f));
        }



//        SpringMass[] springs= {
//                new SpringMass(fixed,mass[0]),
//                new SpringMass(mass[0],mass[1]),
//                new SpringMass(mass[1],mass[2]),
//                new SpringMass(mass[2],mass[3]),
//                new SpringMass(mass[3],mass[4]),
//                new SpringMass(mass[4],mass[5]),
//                new SpringMass(mass[5],mass[6]),
//                new SpringMass(mass[6],mass[7]),
////                new SpringMass(mass[7],fixed2),
//
//
//        };

        SpringMass[] springs= {
                new SpringMass(fixed,mass[0]),
//                new SpringMass(fixed,mass[1]),
//                new SpringMass(fixed,mass[2]),
//                new SpringMass(fixed,mass[3]),
//                new SpringMass(fixed2,mass[0]),
//                new SpringMass(fixed2,mass[1]),
//                new SpringMass(fixed2,mass[2]),
                new SpringMass(fixed2,mass[3]),
                new SpringMass(mass[0],mass[1]),
                new SpringMass(mass[0],mass[4]),

                new SpringMass(mass[1],mass[2]),
                new SpringMass(mass[1],mass[5]),

                new SpringMass(mass[2],mass[6]),
                new SpringMass(mass[2],mass[3]),

                new SpringMass(mass[3],mass[7]),


                new SpringMass(mass[4],mass[5]),
                new SpringMass(mass[4],mass[8]),

                new SpringMass(mass[5],mass[6]),
                new SpringMass(mass[5],mass[9]),


//                new SpringMass(mass[5],mass[6]),
                new SpringMass(mass[6],mass[7]),
                new SpringMass(mass[10],mass[6]),


                new SpringMass(mass[11],mass[7]),

                new SpringMass(mass[8],mass[9]),
                new SpringMass(mass[8],mass[12]),

                new SpringMass(mass[10],mass[11]),
                new SpringMass(mass[10],mass[14]),

                new SpringMass(mass[9],mass[10]),
                new SpringMass(mass[9],mass[13]),


//                new SpringMass(mass[11],mass[12]),
                new SpringMass(mass[11],mass[15]),

                new SpringMass(mass[12],mass[13]),
                new SpringMass(mass[13],mass[14]),

                new SpringMass(mass[14],mass[15]),

//                new SpringMass(mass[2],mass[13]),
//                new SpringMass(mass[10],mass[14]),

        };




//        mass[0].setPosition(new Vec3f(0.2f,0f,0f));
//        mass[1].setPosition(new Vec3f(0.4f,0f,0f));
//        mass[2].setPosition(new Vec3f(0.6f,0f,0f));
//        mass[3].setPosition(new Vec3f(0.8f,0f,0f));
//        for(int j=0;j<n;j++){

//        }


//        mass[3].setMass(Float.POSITIVE_INFINITY);








//        SpringMass spring0 = new SpringMass(fixed,mass0);
////        spring0.setK(5000f);
//        SpringMass spring1 = new SpringMass(mass0,mass1);
//
//        SpringMass spring2 = new SpringMass(mass1,mass2);

        // mass.setMass(mass.mass+mass1.mass+mass2.mass);
       // mass1.setMass(mass1.mass+mass2.mass);
       // spring.setMass(mass);
       // spring1.setMass(mass1);
       // spring2.setMass(mass2);


        float t=0f;
        float dt = 0.0001f;

        springs[1].setK(500f);
        springs[0].setK(500f);




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
            for(int j=0;j<n;j++){
                mass[j].setForce(new Vec3f(0.f,0f,0f));
            }

            for(int i=0;i<springs.length;i++){
                springs[i].giveForce();

                //springs[i].giveForce();
            }
            for(int j=0;j<n;j++){
                mass[j].update(dt);
                System.out.print("f");
                System.out.println(mass[j].force.y);
            }
//            System.out.print("wait 1 ");
//            spring0.giveForce();
//
//            System.out.print("wait 2 ");
//            spring1.giveForce();
//            System.out.print("wait 3 ");
//            spring2.giveForce();
//
//            mass0.update(dt);
//            mass1.update(dt);
//            mass2.update(dt);


            //System.out.println(mass.force.x);
//            renderSphere(-.75f,.75f,0f,mass[0].position.x,mass[0].position.y, mass[0].position.z);
//            renderSphere(.25f,.75f,0f,mass[3].position.x,mass[3].position.y, mass[3].position.z);

            for(int k=0;k<springs.length;k++){
//                springs[k].setStart(springs[k].startmass.position);

                renderSphere(springs[k].getstartMass().position.x,0.5f *springs[k].getstartMass().position.y, springs[k].getstartMass().position.z,springs[k].getMass().position.x,0.5f *springs[k].getMass().position.y, springs[k].getMass().position.z);
            }

//            renderSphere(mass[7].position.x,mass[7].position.y, mass[7].position.z,1f,0f,0f);
//           /nderSphere(mass[1].position.x,mass[1].position.y, mass[1].position.z,mass[2].position.x,mass[2].position.y, mass[2].position.z);



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