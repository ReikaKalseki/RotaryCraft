#import math
#import geometry

uniform float distance;
uniform float factor;
uniform float scale;
uniform float fade;

void main() {
	vec2 focusXY = getScreenPos(0.0, 0.0, 0.0);
	
	float distv = distsq(focusXY, texcoord);
	float distfac_vertex = max(0.0, min(1.0, 2.25-65.0/scale*distv*distance/factor)-factor*fade*min(1.0, 0.008/(distv*distance)));
	float vf = intensity*distfac_vertex*0.05*factor;
	
	float ds = pow(distance, 0.125)/1.5;
	float dv = 1.0+pow(factor, 1.75)*1.5;
	
	vec2 texUV = texcoord.xy;
	texUV.x += 0.47*vf*sin(23.3+texUV.y*51.8*ds+float(time)*dv/4.1);
	texUV.y += 0.62*vf*cos(34.5+texUV.x*45.7*ds+float(time)*dv/3.8);
	texUV.x += 0.167*vf*sin(23.3+texUV.y*171.8*ds+float(time)*dv/6.1);
	texUV.y += 0.145*vf*cos(34.5+texUV.x*185.7*ds+float(time)*dv/5.8);
	
    vec4 color = texture2D(bgl_RenderedTexture, texUV);    
    gl_FragColor = color;
}