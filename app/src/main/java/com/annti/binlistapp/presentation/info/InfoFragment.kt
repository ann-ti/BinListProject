package com.annti.binlistapp.presentation.info

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.annti.binlistapp.R
import com.annti.binlistapp.databinding.FragmentInfoBinding
import com.annti.binlistapp.presentation.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment : Fragment(R.layout.fragment_info) {

    private lateinit var binding: FragmentInfoBinding
    private val args: InfoFragmentArgs by navArgs()
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.title = "Card Info"
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_infoFragment_to_mainFragment)
        }

        setNumberCard()
        setUrlBank()
        setPhoneBank()
        setPrepaidCard()
        setCountryCard()
        deleteCard()

        setCard(binding.typeCard, "TYPE", args.cardInfo.type ?: "No information")
        setCard(binding.schemeCard, "SCHEME / NETWORK", args.cardInfo.scheme ?: "No information")
        setCard(binding.brandCard, "BRAND", args.cardInfo.brand ?: "No information")
        setCard(binding.bankCard, "BANK", args.cardInfo.nameBank ?: "No information")
    }

    private fun deleteCard() {
        binding.toolbar.setOnMenuItemClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Do you confirm the deletion of the card?")
                .setPositiveButton("Yes") { dialog, _ ->
                    viewModel.delete(args.cardInfo)
                    dialog.cancel()
                    findNavController().navigate(R.id.action_infoFragment_to_mainFragment)
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.cancel()
                }
                .show()

            true
        }

    }

    private fun setCard(view: DetailsCardView, textName: String, textValue: String) {
        view.setTextName(textName)
        view.setTextValue(textValue)
    }

    private fun setCountryCard() {
        setCard(
            binding.countryCard,
            "COUNTRY",
            "${args.cardInfo.nameCountry}" +
                    " \n(latitude: ${args.cardInfo.countryLatitude}, " +
                    "longitude: ${args.cardInfo.countryLongitude})"
        )
        val buttonToNextMap = binding.countryCard.navigateToNext()
        buttonToNextMap.visibility = View.VISIBLE
        buttonToNextMap.setOnClickListener {
            val mapIntent =
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("geo:${args.cardInfo.countryLatitude},${args.cardInfo.countryLongitude}?z=5")
                )
            startActivity(mapIntent)
        }
    }

    private fun setPrepaidCard() {
        val prepaid = args.cardInfo.prepaid
        val prepaidValue = if (prepaid != null) {
            if (prepaid) {
                "Yes"
            } else "No"
        } else "No information"
        setCard(binding.prepaidCard, "PREPAID", prepaidValue)
    }

    private fun setNumberCard() {
        val luhn = args.cardInfo.luhn
        val luhnValue =
            if (luhn != null) {
                if (luhn) {
                    "Yes"
                } else "No"
            } else "No information"
        setCard(
            binding.numberCard,
            "CARD NUMBER",
            "LENGTH: ${args.cardInfo.lengthCardNumber}, LUHN: $luhnValue"
        )
    }

    private fun setUrlBank() {
        setCard(binding.urlBankCard, "URL BANK", args.cardInfo.bankUrl ?: "args.cardInfo.bank.url")
        val buttonToNextBrowser = binding.urlBankCard.navigateToNext()
        buttonToNextBrowser.visibility = View.VISIBLE
        buttonToNextBrowser.setOnClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("http://${args.cardInfo.bankUrl}"))
            startActivity(browserIntent)
        }
    }

    private fun setPhoneBank() {
        if (args.cardInfo.bankPhone.isNullOrEmpty()) {
            binding.phoneBankCard.setTextValue("Телефон банка отсутствует")

        } else {
            binding.phoneBankCard.setTextValue(args.cardInfo.bankPhone!!)
            val buttonToNextPhone = binding.phoneBankCard.navigateToNext()
            buttonToNextPhone.visibility = View.VISIBLE
            buttonToNextPhone.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + args.cardInfo.bankPhone))
                startActivity(intent)
            }
        }
        binding.phoneBankCard.setTextName("PHONE BANK")

    }
}