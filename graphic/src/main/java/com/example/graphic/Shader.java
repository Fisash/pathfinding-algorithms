package com.example.graphic;
import org.lwjgl.opengl.GL33;
import static org.lwjgl.opengl.GL33.*;

public class Shader {
    private final int shaderProgramPtr;

    public Shader(String vertexSource, String fragmentSource) {
        int vertexShader = compileShader(GL_VERTEX_SHADER, vertexSource);
        int fragmentShader = compileShader(GL_FRAGMENT_SHADER, fragmentSource);

        //link program
        shaderProgramPtr = glCreateProgram();
        glAttachShader(shaderProgramPtr, vertexShader);
        glAttachShader(shaderProgramPtr, fragmentShader);
        glLinkProgram(shaderProgramPtr);

        //check
        int success = glGetProgrami(shaderProgramPtr, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            String log = glGetProgramInfoLog(shaderProgramPtr);
            throw new RuntimeException("Error of link shader program:\n" + log);
        }

        glDetachShader(shaderProgramPtr, vertexShader);
        glDetachShader(shaderProgramPtr, fragmentShader);
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }

    private int compileShader(int type, String source) {
        int shader = glCreateShader(type);
        glShaderSource(shader, source);
        glCompileShader(shader);

        int success = glGetShaderi(shader, GL_COMPILE_STATUS);
        if (success == GL_FALSE) {
            String log = glGetShaderInfoLog(shader);
            throw new RuntimeException("Error of compile shader:\n" + log);
        }
        return shader;
    }

    public void use() {
        glUseProgram(shaderProgramPtr);
    }

    public void delete() {
        glDeleteProgram(shaderProgramPtr);
    }

    public int getProgram() {
        return shaderProgramPtr;
    }
}
