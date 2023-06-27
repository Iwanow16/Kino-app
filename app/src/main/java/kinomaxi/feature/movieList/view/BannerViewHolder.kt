package kinomaxi.feature.movieList.view

import androidx.recyclerview.widget.RecyclerView
import kinomaxi.databinding.ItemBannerBinding

/**
 * [RecyclerView.ViewHolder] для отображения карточки баннера в списке избранных
 */

class BannerViewHolder(
    viewBinding: ItemBannerBinding,
) : RecyclerView.ViewHolder(viewBinding.root) {

    private val textView = viewBinding.BannerMessage

    /**
     * Установить данные [data] для отображения
     */
    fun setData(data: MovieListItem.Banner) {
        textView.text = data.text
    }
}