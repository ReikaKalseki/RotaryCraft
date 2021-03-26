uniform int glowRed;
uniform int glowGreen;
uniform int glowBlue;

void main() {	
    vec4 color = gl_Color;
	/*
	color.a = max(0.0, color.a-2.0/255.0);
	
	vec3 glow = vec3(float(glowRed)/255.0, float(glowGreen)/255.0, float(glowBlue)/255.0);
	color.r = min(1.0, color.r+glow.r*color.a);
	color.g = min(1.0, color.g+glow.g*color.a);
	color.b = min(1.0, color.b+glow.b*color.a);
	color.rgb = mix(color.rgb, glow, color.a/8.0);
	*/
    gl_FragColor = vec4(color.x, color.y, color.z, 1.0);
}