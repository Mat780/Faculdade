#include "branch_m_n_predictor.hpp"
#include "math.h"

branch_m_n_predictor::branch_m_n_predictor(unsigned int m, unsigned int n): m_bits(m), n_bits(n)
{
    max_N = (1<<n_bits)-1;
    max_M = (1<<m_bits)-1;
    globalHistoric = 0;
    c_predictions = 0;
    c_hits = 0;
    bmn_buffer.resize(pow(2, m));

}

bool branch_m_n_predictor::predictor_m_n_predict()
{
    c_predictions++;
    
    int state = bmn_buffer.at(globalHistoric);

    return state & (1<<(n_bits-1));
}
void branch_m_n_predictor::predictor_m_n_update_state(bool taken, bool hit)
{
    int stateLocal_aux = bmn_buffer.at(globalHistoric);

    c_hits += hit;


    if(taken)
    {
        stateLocal_aux += (stateLocal_aux < max_N);
    }
    else
    {
        stateLocal_aux -= (stateLocal_aux > 0);
    }

    bmn_buffer.at(globalHistoric) = stateLocal_aux;


    int stateGlobal_aux = globalHistoric << 1;
    
    if(taken)
    {
        stateGlobal_aux += 1;
    }

    if(stateGlobal_aux > max_M) 
    {
        stateGlobal_aux -= max_M + 1;
    }

    globalHistoric = stateGlobal_aux;

}

float branch_m_n_predictor::get_predictor_m_n_hit_rate() {
    cout << "# Numero de desvios: " << c_predictions << endl;
    return (float) c_hits / (float) c_predictions * 100;
}

int branch_m_n_predictor::get_m_value() {
    return m_bits;
}

int branch_m_n_predictor::get_n_value() {
    return n_bits;
}