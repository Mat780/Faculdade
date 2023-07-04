#include <vector>
#include <iostream>

using namespace std;

class branch_m_n_predictor
{
    public:
        branch_m_n_predictor(unsigned int m, unsigned int n);
        bool predictor_m_n_predict();
        void predictor_m_n_update_state(bool taken, bool hit);
        float get_predictor_m_n_hit_rate();
        int get_m_value();
        int get_n_value();

    private:
        std::vector<int> bmn_buffer;
        int m_bits, n_bits, max_M, max_N, globalHistoric, c_predictions, c_hits;
};

