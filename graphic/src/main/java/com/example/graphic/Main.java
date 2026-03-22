package com.example.graphic;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

public class Main {
    private Window window;
    private Shader shader;
    private int vao;

    private static final String VERTEX_SHADER_SOURCE = """
        #version 330 core
        layout (location = 0) in vec3 aPos;
        void main() {
            gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);
        }
    """;

    private static final String FRAGMENT_SHADER_SOURCE = """
        #version 330 core
        out vec4 FragColor;
        void main() {
            FragColor = vec4(0.1, 0.5, 0.2, 1.0);
        }
    """;

    public void run() {
        init();
        loop();
        cleanup();
    }

    private void init() {
        window = new Window(800, 600, "fuck");

        GL.createCapabilities();
        shader = new Shader(VERTEX_SHADER_SOURCE, FRAGMENT_SHADER_SOURCE);
        shader.use();

        // Вершинные данные
        float[] vertices = {
            -0.2f, -0.5f, 0.0f,
             0.5f, -0.9f, 0.0f,
             0.0f,  0.5f, 0.0f
        };

        vao = GL33.glGenVertexArrays();
        GL33.glBindVertexArray(vao);

        int vbo = GL33.glGenBuffers();
        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, vbo);

        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.floats(vertices);
            GL33.glBufferData(GL33.GL_ARRAY_BUFFER, fb, GL33.GL_STATIC_DRAW);
        }

        GL33.glVertexAttribPointer(0, 3, GL33.GL_FLOAT, false, 3 * Float.BYTES, 0);
        GL33.glEnableVertexAttribArray(0);

        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, 0);
        GL33.glBindVertexArray(0);
    }

    private void loop() {
        while (!window.isShouldClose()) {
            window.pollEvents();

            GL33.glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
            GL33.glClear(GL33.GL_COLOR_BUFFER_BIT);

            shader.use();
            GL33.glBindVertexArray(vao);
            GL33.glDrawArrays(GL33.GL_TRIANGLES, 0, 3);

            window.swapBuffers();
        }
    }

    private void cleanup() {
        shader.delete();
        GL33.glDeleteVertexArrays(vao);
        //todo destroy vbo
        window.destroy();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
