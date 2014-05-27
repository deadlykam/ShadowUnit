#import "Common/ShaderLib/Parallax.glsllib"
#import "Common/ShaderLib/Optics.glsllib"
#define ATTENUATION
//#define HQ_ATTENUATION

varying vec2 texCoord;
#ifdef SEPARATE_TEXCOORD
  varying vec2 texCoord2;
#endif

varying vec3 AmbientSum;
varying vec4 DiffuseSum;
varying vec3 SpecularSum;

#ifndef VERTEX_LIGHTING
  uniform vec4 g_LightDirection;
//  varying vec3 vPosition;
  varying vec3 vViewDir;
  varying vec4 vLightDir;
  varying vec3 lightVec;
#else
  varying vec2 vertexLightValues;
#endif

#ifdef DIFFUSEMAP
  uniform sampler2D m_DiffuseMap;
#endif

//Material Parameters for Sequences
#ifdef SEQ1
    uniform sampler2D m_Seq1;
#endif

#ifdef SEQ2
    uniform sampler2D m_Seq2;
#endif

#ifdef SEQ3
    uniform sampler2D m_Seq3;
#endif

#ifdef SEQ4
    uniform sampler2D m_Seq4;
#endif

#ifdef SEQ5
    uniform sampler2D m_Seq5;
#endif

#ifdef SEQUENCING
    uniform vec2 m_Sequencing;
#endif
//===End

#ifdef SPECULARMAP
  uniform sampler2D m_SpecularMap;
#endif

#ifdef PARALLAXMAP
  uniform sampler2D m_ParallaxMap;  
#endif
#if (defined(PARALLAXMAP) || (defined(NORMALMAP_PARALLAX) && defined(NORMALMAP))) && !defined(VERTEX_LIGHTING) 
    uniform float m_ParallaxHeight;
#endif

#ifdef LIGHTMAP
  uniform sampler2D m_LightMap;
#endif
  
#ifdef NORMALMAP
  uniform sampler2D m_NormalMap;   
#else
  varying vec3 vNormal;
#endif

#ifdef ALPHAMAP
  uniform sampler2D m_AlphaMap;
#endif

#ifdef COLORRAMP
  uniform sampler2D m_ColorRamp;
#endif

 uniform float m_AlphaDiscardThreshold;

#ifndef VERTEX_LIGHTING
uniform float m_Shininess;

#ifdef HQ_ATTENUATION
uniform vec4 g_LightPosition;
#endif

#ifdef USE_REFLECTION 
    uniform float m_ReflectionPower;
    uniform float m_ReflectionIntensity;
    varying vec4 refVec;

    uniform ENVMAP m_EnvMap;
#endif

#ifdef DISSOLVE_MAP
    uniform sampler2D m_DissolveMap;
    #ifdef DISSOLVE_PARAMS
        uniform vec2 m_DissolveParams;
    #endif

    #ifdef MOVEDISSOLVE
        uniform vec2 m_MoveDissolve;
    #endif
#endif

float tangDot(in vec3 v1, in vec3 v2){
    float d = dot(v1,v2);
    #ifdef V_TANGENT
        d = 1.0 - d*d;
        return step(0.0, d) * sqrt(d);
    #else
        return d;
    #endif
}

float lightComputeDiffuse(in vec3 norm, in vec3 lightdir, in vec3 viewdir){
    #ifdef MINNAERT
        float NdotL = max(0.0, dot(norm, lightdir));
        float NdotV = max(0.0, dot(norm, viewdir));
        return NdotL * pow(max(NdotL * NdotV, 0.1), -1.0) * 0.5;
    #else
        return max(0.0, dot(norm, lightdir));
    #endif
}

float lightComputeSpecular(in vec3 norm, in vec3 viewdir, in vec3 lightdir, in float shiny){
    if (shiny <= 1.0){
        return 0.0;
    }
    #ifdef LOW_QUALITY
       // Blinn-Phong
       // Note: preferably, H should be computed in the vertex shader
       vec3 H = (viewdir + lightdir) * vec3(0.5);
       return pow(max(tangDot(H, norm), 0.0), shiny);
    #elif defined(WARDISO)
        // Isotropic Ward
        vec3 halfVec = normalize(viewdir + lightdir);
        float NdotH  = max(0.001, tangDot(norm, halfVec));
        float NdotV  = max(0.001, tangDot(norm, viewdir));
        float NdotL  = max(0.001, tangDot(norm, lightdir));
        float a      = tan(acos(NdotH));
        float p      = max(shiny/128.0, 0.001);
        return NdotL * (1.0 / (4.0*3.14159265*p*p)) * (exp(-(a*a)/(p*p)) / (sqrt(NdotV * NdotL)));
    #else
       // Standard Phong
       vec3 R = reflect(-lightdir, norm);
       return pow(max(tangDot(R, viewdir), 0.0), shiny);
    #endif
}

vec2 computeLighting(in vec3 wvNorm, in vec3 wvViewDir, in vec3 wvLightDir){
   float diffuseFactor = lightComputeDiffuse(wvNorm, wvLightDir, wvViewDir);
   float specularFactor = lightComputeSpecular(wvNorm, wvViewDir, wvLightDir, m_Shininess);

   #ifdef HQ_ATTENUATION
    float att = clamp(1.0 - g_LightPosition.w * length(lightVec), 0.0, 1.0);
   #else
    float att = vLightDir.w;
   #endif

if (m_Shininess <= 1.0) {
specularFactor = 0.0; // should be one instruction on most cards ..
}

   specularFactor *= diffuseFactor;

   return vec2(diffuseFactor, specularFactor) * vec2(att);
}
#endif

void main(){
    vec2 newTexCoord;
     
    

    #if (defined(PARALLAXMAP) || (defined(NORMALMAP_PARALLAX) && defined(NORMALMAP))) && !defined(VERTEX_LIGHTING) 
     
       #ifdef STEEP_PARALLAX
           #ifdef NORMALMAP_PARALLAX
               //parallax map is stored in the alpha channel of the normal map         
               newTexCoord = steepParallaxOffset(m_NormalMap, vViewDir, texCoord, m_ParallaxHeight);
           #else
               //parallax map is a texture
               newTexCoord = steepParallaxOffset(m_ParallaxMap, vViewDir, texCoord, m_ParallaxHeight);         
           #endif
       #else
           #ifdef NORMALMAP_PARALLAX
               //parallax map is stored in the alpha channel of the normal map         
               newTexCoord = classicParallaxOffset(m_NormalMap, vViewDir, texCoord, m_ParallaxHeight);
           #else
               //parallax map is a texture
               newTexCoord = classicParallaxOffset(m_ParallaxMap, vViewDir, texCoord, m_ParallaxHeight);
           #endif
       #endif
    #else
       newTexCoord = texCoord;    
    #endif

    #ifdef DISSOLVE_MAP
        #ifdef MOVEDISSOLVE
            newTexCoord.x = newTexCoord.x + m_MoveDissolve.x;
            newTexCoord.y = newTexCoord.y + m_MoveDissolve.y;
        #endif
        
        #ifdef DISSOLVE_PARAMS
            if (m_DissolveParams.y == 0.0 && texture2D(m_DissolveMap, newTexCoord).r < m_DissolveParams.x) {
                discard;
            }
            if (m_DissolveParams.y == 1.0 && texture2D(m_DissolveMap, newTexCoord).r > m_DissolveParams.x) {
                discard;
            }
        #endif
    #endif
    
    vec4 diffuseColor = vec4(1.0);
    //Sequencing Code
    #ifdef SEQUENCING
        if(m_Sequencing.x == 1){
            #ifdef SEQ1
                diffuseColor *= texture2D(m_Seq1, texCoord);
            #endif
        }else if(m_Sequencing.x == 2){
            #ifdef SEQ2
                diffuseColor *= texture2D(m_Seq2, texCoord);
            #endif        
        }else if(m_Sequencing.x == 3){
            #ifdef SEQ3
                diffuseColor *= texture2D(m_Seq3, texCoord);
            #endif
        }else if(m_Sequencing.x == 4){
            #ifdef SEQ4
                diffuseColor *= texture2D(m_Seq4, texCoord);
            #endif
        }else if(m_Sequencing.x == 5){
            #ifdef SEQ5
                diffuseColor *= texture2D(m_Seq5, texCoord);
            #endif
        }
    #endif
    //===END
    
   #ifdef DIFFUSEMAP
      diffuseColor = texture2D(m_DiffuseMap, newTexCoord);
   // #else
  //    diffuseColor = vec4(1.0);
    #endif

    float alpha = DiffuseSum.a * diffuseColor.a;
    #ifdef ALPHAMAP
       alpha = alpha * texture2D(m_AlphaMap, newTexCoord).r;
    #endif

    if(alpha < m_AlphaDiscardThreshold){
        discard;
    }

    #ifndef VERTEX_LIGHTING
        float spotFallOff = 1.0;
        if(g_LightDirection.w != 0.0){
              vec3 L       = normalize(lightVec.xyz);
              vec3 spotdir = normalize(g_LightDirection.xyz);
              float curAngleCos = dot(-L, spotdir);             
              float innerAngleCos = floor(g_LightDirection.w) * 0.001;
              float outerAngleCos = fract(g_LightDirection.w);
              float innerMinusOuter = innerAngleCos - outerAngleCos;

              spotFallOff = (curAngleCos - outerAngleCos) / innerMinusOuter;

              if(spotFallOff <= 0.0){
                  gl_FragColor.rgb = AmbientSum * diffuseColor.rgb;
                  gl_FragColor.a   = alpha;
                  return;
              }else{
                  spotFallOff = clamp(spotFallOff, 0.0, 1.0);
              }
        }
     #endif
 
    // ***********************
    // Read from textures
    // ***********************
    #if defined(NORMALMAP) && !defined(VERTEX_LIGHTING)
      vec4 normalHeight = texture2D(m_NormalMap, newTexCoord);
      vec3 normal = (normalHeight.xyz * vec3(2.0) - vec3(1.0));
      #ifdef LATC
        normal.z = sqrt(1.0 - (normal.x * normal.x) - (normal.y * normal.y));
      #endif
      //normal.y = -normal.y;
    #elif !defined(VERTEX_LIGHTING)
      vec3 normal = vNormal;
      #if !defined(LOW_QUALITY) && !defined(V_TANGENT)
         normal = normalize(normal);
      #endif
    #endif

    #ifdef SPECULARMAP
      vec4 specularColor = texture2D(m_SpecularMap, newTexCoord);
    #else
      vec4 specularColor = vec4(1.0);
    #endif

    #ifdef LIGHTMAP
       vec3 lightMapColor;
       #ifdef SEPARATE_TEXCOORD
          lightMapColor = texture2D(m_LightMap, texCoord2).rgb;
       #else
          lightMapColor = texture2D(m_LightMap, texCoord).rgb;
       #endif
       specularColor.rgb *= lightMapColor;
       diffuseColor.rgb  *= lightMapColor;
    #endif

    #ifdef VERTEX_LIGHTING
       #ifdef COLORRAMP
           light.x = texture2D(m_ColorRamp, vec2(vertexLightValues.x, 0.0)).r;
           light.y = texture2D(m_ColorRamp, vec2(vertexLightValues.y, 0.0)).r;
       #endif

       gl_FragColor.rgb =  AmbientSum     * diffuseColor.rgb + 
                           DiffuseSum.rgb * diffuseColor.rgb  * vec3(vertexLightValues.x) +
                           SpecularSum    * specularColor.rgb * vec3(vertexLightValues.y);
    #else
       vec4 lightDir = vLightDir;
       lightDir.xyz = normalize(lightDir.xyz);

       vec2   light = computeLighting(normal, vViewDir.xyz, lightDir.xyz) * spotFallOff;
       #ifdef COLORRAMP
           diffuseColor.rgb  *= texture2D(m_ColorRamp, vec2(light.x, 0.0)).rgb;
           specularColor.rgb *= texture2D(m_ColorRamp, vec2(light.y, 0.0)).rgb;
       #endif

       // Workaround, since it is not possible to modify varying variables
       vec4 SpecularSum2 = vec4(SpecularSum, 1.0);
       #ifdef USE_REFLECTION
            vec4 refColor = Optics_GetEnvColor(m_EnvMap, refVec.xyz);

            // Interpolate light specularity toward reflection color
            // Multiply result by specular map
            specularColor = mix(SpecularSum2 * light.y, refColor, refVec.w) * specularColor;

            SpecularSum2 = vec4(1.0);
            light.y = 1.0;
       #endif

       gl_FragColor.rgb =  AmbientSum       * diffuseColor.rgb  +
                           DiffuseSum.rgb   * diffuseColor.rgb  * vec3(light.x) +
                           SpecularSum2.rgb * specularColor.rgb * vec3(light.y);
    #endif
    gl_FragColor.a = alpha;
}