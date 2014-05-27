attribute vec3 inPosition;
attribute vec2 inTexCoord;

uniform mat4 g_WorldViewProjectionMatrix;

varying vec2 texCoord;

/*
* vertex shader template
*/
void main() { 
    // Vertex transformation 
    vec4 position = vec4(inPosition, 1.0);
    texCoord = inTexCoord;
    gl_Position = g_WorldViewProjectionMatrix * position; 
}
