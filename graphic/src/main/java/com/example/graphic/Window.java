package com.example.graphic;
import org.lwjgl.glfw.GLFW;

public class Window {

    private final long windowPtr;
    
    Window(int width, int height, String title) {
        if (!GLFW.glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);

        windowPtr = GLFW.glfwCreateWindow(width, height, title, 0, 0);
        if (windowPtr == 0)
            throw new RuntimeException("Failed to create window");
        GLFW.glfwMakeContextCurrent(windowPtr);
        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(windowPtr);
    }

    public boolean isShouldClose() {
        return GLFW.glfwWindowShouldClose(windowPtr);
    }

    public void destroy() {
        GLFW.glfwDestroyWindow(windowPtr);
        GLFW.glfwTerminate();
    }
    
    public void swapBuffers() {
        GLFW.glfwSwapBuffers(windowPtr);
    }
    
    public void pollEvents() {
        GLFW.glfwPollEvents();
    }
}
