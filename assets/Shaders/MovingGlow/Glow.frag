
#if defined(NEED_TEXCOORD1) 
    varying vec2 texCoord1;
#else 
    varying vec2 texCoord;
#endif

#ifdef GLOWMAP
  uniform sampler2D m_GlowMap;
#endif

#ifdef GLOWMAP1
  uniform sampler2D m_GlowMap1;
#endif

#ifdef GLOWMAP2
  uniform sampler2D m_GlowMap2;
#endif

#ifdef GLOWMAP3
  uniform sampler2D m_GlowMap3;
#endif

#ifdef GLOWMAP4
  uniform sampler2D m_GlowMap4;
#endif

#ifdef GLOWMAP5
  uniform sampler2D m_GlowMap5;
#endif

#ifdef GLOWSEQUENCING
    uniform vec2 m_GlowSequencing;
#endif

#ifdef HAS_GLOWCOLOR
  uniform vec4 m_GlowColor;
#endif

#ifdef GLOW_DISSOLVE_MAP
    uniform sampler2D  m_Glow_DissolveMap;
    #ifdef GLOW_DISSOLVE_PARAMS
        uniform vec2 m_Glow_DissolveParams;
    #endif

    #ifdef MOVEGLOW
        uniform vec2 m_MoveGlow;
    #endif
#endif

void main(){
    #ifdef GLOWSEQUENCING
        if(m_GlowSequencing.x == 1){
            #ifdef GLOWMAP1
                #if defined(NEED_TEXCOORD1) 
                   gl_FragColor = texture2D(m_GlowMap1, texCoord1);
                #else 
                   gl_FragColor = texture2D(m_GlowMap1, texCoord);
                #endif
            #endif
        }else if(m_GlowSequencing.x == 2){
            #ifdef GLOWMAP2
                #if defined(NEED_TEXCOORD1) 
                   gl_FragColor = texture2D(m_GlowMap2, texCoord1);
                #else 
                   gl_FragColor = texture2D(m_GlowMap2, texCoord);
                #endif
            #endif
        }else if(m_GlowSequencing.x == 3){
            #ifdef GLOWMAP3
                #if defined(NEED_TEXCOORD1) 
                   gl_FragColor = texture2D(m_GlowMap3, texCoord1);
                #else 
                   gl_FragColor = texture2D(m_GlowMap3, texCoord);
                #endif
            #endif
        }else if(m_GlowSequencing.x == 4){
            #ifdef GLOWMAP4
                #if defined(NEED_TEXCOORD1) 
                   gl_FragColor = texture2D(m_GlowMap4, texCoord1);
                #else 
                   gl_FragColor = texture2D(m_GlowMap4, texCoord);
                #endif
            #endif
        }else if(m_GlowSequencing.x == 5){
            #ifdef GLOWMAP5
                #if defined(NEED_TEXCOORD1) 
                   gl_FragColor = texture2D(m_GlowMap5, texCoord1);
                #else 
                   gl_FragColor = texture2D(m_GlowMap5, texCoord);
                #endif
            #endif
        }
    #else
        #ifdef GLOWMAP
            #if defined(NEED_TEXCOORD1) 
               gl_FragColor = texture2D(m_GlowMap, texCoord1);
            #else 
               gl_FragColor = texture2D(m_GlowMap, texCoord);
            #endif
        #else
            #ifdef HAS_GLOWCOLOR
                gl_FragColor =  m_GlowColor;
            #else
                gl_FragColor = vec4(0.0);
            #endif
        #endif
    #endif
    
    vec2 newTexCoord = vec2(1.0);
    #if defined(NEED_TEXCOORD1) 
       newTexCoord = texCoord1;
    #else 
       newTexCoord = texCoord;
    #endif

    #ifdef GLOW_DISSOLVE_MAP
        #ifdef MOVEGLOW
            newTexCoord.x = newTexCoord.x + m_MoveGlow.x;
            newTexCoord.y = newTexCoord.y + m_MoveGlow.y;
        #endif

        #ifdef GLOW_DISSOLVE_PARAMS
            if (m_Glow_DissolveParams.y == 0.0 && texture2D(m_Glow_DissolveMap, newTexCoord).r < m_Glow_DissolveParams.x) {
                discard;
            }
            if (m_Glow_DissolveParams.y == 1.0 && texture2D(m_Glow_DissolveMap, newTexCoord).r > m_Glow_DissolveParams.x) {
                discard;
            }
        #endif
    #endif
}