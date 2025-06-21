package com.example.fancycolordiamonds.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fancycolordiamonds.CommunicatorProduct
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

        /* Свадьба и помолвка */
        btnSolitaireRings.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 6)
        }

        btnRingsHalo.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 7)
        }

        btnRingsWithThreeRings.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 8)
        }

        btnRingsWithSideStones.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 9)
        }

        btnFancyColorRingsOfEternity.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 10)
        }

        btnDiamondRingsOfEternity.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 11)
        }

        btnWeddingRings.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 12)
        }

        btnWeddingRingsTwo.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 13)
        }

        btnMensRings.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 14)
        }

        /* Остальные */
        btnDiamonds.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 15)
        }

        btnStones.setOnClickListener { view: View ->
            communicatorProduct.getDataCatalog(ProductFragment(), 16)
        }

        btnJewelry.setOnClickListener { view: View ->
            if (propBtn1 == false) {
                btnNew.visibility = View.VISIBLE
                btnRings.visibility = View.VISIBLE
                btnEarrings.visibility = View.VISIBLE
                btnObesitySuspension.visibility = View.VISIBLE
                btnBracelets.visibility = View.VISIBLE
                btnRecentlySold.visibility = View.VISIBLE
                propBtn1 = true
            }
            else {
                btnNew.visibility = View.GONE
                btnRings.visibility = View.GONE
                btnEarrings.visibility = View.GONE
                btnObesitySuspension.visibility = View.GONE
                btnBracelets.visibility = View.GONE
                btnRecentlySold.visibility = View.GONE
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
                propBtn2 = false
            }
        }
    }
}