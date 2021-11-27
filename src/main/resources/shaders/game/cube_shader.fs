#version 330 core

in vec4 vColor;

out vec4 fragColor;

void main()
{
	fragColor = vColor;
	//fragColor = vec4(1, 0, 1, 1);
}