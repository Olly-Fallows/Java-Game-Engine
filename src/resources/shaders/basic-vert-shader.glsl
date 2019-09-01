# version 400 core

in vec3 vertices;
in vec2 textureCoords;

out vec2 textCoords;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main() {
	gl_Position = projection * view * model * vec4(vertices, 1.0);
	textCoords = textureCoords;
}
