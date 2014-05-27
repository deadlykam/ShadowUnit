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

//Material Parameter for Dissolve
#ifdef DISSOLVEMAP
    uniform sampler2D m_DissolveMap;
#endif

#ifdef BLEND1
    uniform vec2 m_Blend1;
#endif
//===End

varying vec4 texCoord;



/*
* fragment shader template
*/
void main() {
    vec4 color = vec4(1.0);

    //Sequencing Code
    #ifdef SEQUENCING
        if(m_Sequencing.x == 1){
            #ifdef SEQ1
                color *= texture2D(m_Seq1, texCoord);
            #endif
        }else if(m_Sequencing.x == 2){
            #ifdef SEQ2
                color *= texture2D(m_Seq2, texCoord);
            #endif        
        }else if(m_Sequencing.x == 3){
            #ifdef SEQ3
                color *= texture2D(m_Seq3, texCoord);
            #endif
        }else if(m_Sequencing.x == 4){
            #ifdef SEQ4
                color *= texture2D(m_Seq4, texCoord);
            #endif
        }else if(m_Sequencing.x == 5){
            #ifdef SEQ5
                color *= texture2D(m_Seq5, texCoord);
            #endif
        }
    #endif
    //===END

    //Dissolve Code
    #ifdef BLEND1
        if(m_Blend1.y == 0){
            //This Reduces the alpha of the colormap
            color = color * m_Blend1.x;
        }else if(m_Blend1.y == 1){
            //This uses the dissolve map to dissolve the texture
            #ifdef DISSOLVEMAP
                if(texture2D(m_DissolveMap, texCoord).r < m_Blend1.x){
                    discard;
                }
            #endif
        }
    #endif
    //===END

    gl_FragColor = color;  
}

