package com.fancycolor.apk.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fancycolor.apk.CommunicatorProduct
import com.fancycolor.apk.R
import kotlinx.android.synthetic.main.fragment_catalog.*

class CatalogFragment : Fragment() {
    private var propBtn1 = false
    private var propBtn2 = false
    private lateinit var communicatorProduct: CommunicatorProduct

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catalog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        communicatorProduct = activity as CommunicatorProduct
        propBtn1 = false
        propBtn2 = false

        /* Ювелирные изделия */
        btnNew.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 0)
        }

        btnRings.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 1)
        }

        btnEarrings.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 2)
        }

        btnObesitySuspension.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 3)
        }

        btnBracelets.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 4)
        }

        btnRecentlySold.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 5)
        }

        btnColoredBliliants.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 6)
        }

        btnBlillants.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 7)
        }

        btnGems.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 8)
        }

        /* Свадьба и помолвка */
        btnSolitaireRings.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 9)
        }

        btnRingsHalo.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 10)
        }

        btnRingsWithThreeRings.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 11)
        }

        btnRingsWithSideStones.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 12)
        }

        btnFancyColorRingsOfEternity.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 13)
        }

        btnDiamondRingsOfEternity.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 14)
        }

        btnWeddingRings.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 15)
        }

        btnWeddingRingsTwo.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 16)
        }

        btnMensRings.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 17)
        }

        /* Остальные */
        btnDiamonds.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 18)
        }

        btnStones.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 19)
        }

        btnJewelry.setOnClickListener { view: View ->
            if (propBtn1 == false) {
                btnCategoryInfo.visibility = View.VISIBLE
                btnNew.visibility = View.VISIBLE
                btnRings.visibility = View.VISIBLE
                btnEarrings.visibility = View.VISIBLE
                btnObesitySuspension.visibility = View.VISIBLE
                btnBracelets.visibility = View.VISIBLE
                btnRecentlySold.visibility = View.VISIBLE
                btnRockInfo.visibility = View.VISIBLE
                btnColoredBliliants.visibility = View.VISIBLE
                btnBlillants.visibility = View.VISIBLE
                btnGems.visibility = View.VISIBLE
                propBtn1 = true
            }
            else {
                btnCategoryInfo.visibility = View.GONE
                btnNew.visibility = View.GONE
                btnRings.visibility = View.GONE
                btnEarrings.visibility = View.GONE
                btnObesitySuspension.visibility = View.GONE
                btnBracelets.visibility = View.GONE
                btnRecentlySold.visibility = View.GONE
                btnRockInfo.visibility = View.GONE
                btnColoredBliliants.visibility = View.GONE
                btnBlillants.visibility = View.GONE
                btnGems.visibility = View.GONE
                propBtn1 = false
            }
        }

        btnWeddingEngagement.setOnClickListener { view: View ->
            if (propBtn2 == false) {
                btnSolitaireRings.visibility = View.VISIBLE
                btnRingsHalo.visibility = View.VISIBLE
                btnRingsWithThreeRings.visibility = View.VISIBLE
                btnRingsWithSideStones.visibility = View.VISIBLE
                btnFancyColorRingsOfEternity.visibility = View.VISIBLE
                btnDiamondRingsOfEternity.visibility = View.VISIBLE
                btnWeddingRings.visibility = View.VISIBLE
                btnWeddingRingsTwo.visibility = View.VISIBLE
                btnMensRings.visibility = View.VISIBLE
                btnStyleInfo.visibility = View.VISIBLE
                btnWeddingsInfo.visibility = View.VISIBLE
                propBtn2 = true
            }
            else {
                btnSolitaireRings.visibility = View.GONE
                btnRingsHalo.visibility = View.GONE
                btnRingsWithThreeRings.visibility = View.GONE
                btnRingsWithSideStones.visibility = View.GONE
                btnFancyColorRingsOfEternity.visibility = View.GONE
                btnDiamondRingsOfEternity.visibility = View.GONE
                btnWeddingRings.visibility = View.GONE
                btnWeddingRingsTwo.visibility = View.GONE
                btnMensRings.visibility = View.GONE
                btnStyleInfo.visibility = View.GONE
                btnWeddingsInfo.visibility = View.GONE
                propBtn2 = false
            }
        }
    }
}