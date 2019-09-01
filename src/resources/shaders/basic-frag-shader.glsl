# version 400 core

in vec2 textCoords;

out vec4 fragColour;

uniform sampler2D tex;

void main() {
	fragColour = vec4(textCoords, 0f, 0f);//texture(tex, textCoords);
	//fragColour = vec4((fragVert+0.5f)*0.5,1.0f);
}
