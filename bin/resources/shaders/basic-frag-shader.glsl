# version 400 core

in vec3 fragVert;

out vec4 fragColour;

void main() {
	fragColour = vec4((fragVert+0.5f)*0.5,1.0f);
}
