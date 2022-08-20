package com.jaax.retrofitmvp.data

import com.jaax.retrofitmvp.data.model.Pokemon
import com.jaax.retrofitmvp.data.network.PokemonService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonModel(
    private val presenter: PokemonPresenter,
    private val service: PokemonService) : PokemonMVP.Model {

    override suspend fun getPokemon(name: String) {
        val call = service.getPokemonInfo(name)

        call.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                if(response.isSuccessful) {
                    presenter.setPokemonSingle(response.body()!!)
                    presenter.stopProgressbar()
                } else {
                    presenter.showNotSuccess(response.message())
                }
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                presenter.showError(t.message!!)
            }
        })
    }
}