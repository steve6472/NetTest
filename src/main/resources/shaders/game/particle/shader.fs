#version 330 core

in vec2 vTexture;

out vec4 fragColor;

uniform vec4 color;
uniform sampler2D tex;

void main()
{
	vec4 t = texture(tex, vTexture);
	if (t.a == 0)
		discard;
	t *= color;
	fragColor = t;
}