package com.fancycolor.apk.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.fancycolor.apk.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_share_dialog.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage



class ShareDialogFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_share_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments

        Picasso.get().load(bundle?.getString("img")).into(imageProduct)
        textMessage.text = "Мы знаем, что ________ мечтает о ${bundle?.getString("name")}, поэтому мы решили Вам намекнуть об этом."
        productName.text = bundle?.getString("name")

        sendEmail.setOnClickListener {
            if (editTextNameShare.text.isNotEmpty() && editTextEmailShare.text.isNotEmpty() && editTextNameYourShare.text.isNotEmpty()) {
                try {
                    Thread {
                        Transport.send(plainMail(editTextNameShare.text.toString(), editTextNameYourShare.text.toString(), editTextEmailShare.text.toString(), bundle?.getString("name"), bundle?.getString("img").toString()))
                    }.start()
                    Toast.makeText(activity as FragmentActivity, "Сообщение успешно доставлено!", Toast.LENGTH_SHORT).show()
                    val fragment = (activity as FragmentActivity).supportFragmentManager
                    fragment.popBackStack()
                } catch (e: Exception) {
                    Toast.makeText(activity as FragmentActivity, "Ошибка соединения", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(activity as FragmentActivity, "Заполните пустые поля!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun plainMail(name: String, yourName: String, email: String, productName: String?, img: String): MimeMessage {
        val tos = arrayListOf(email)
        val from = "moscow@fancycolor.world"

        val properties = System.getProperties()

        with (properties) {
            put("mail.smtp.host", "smtp.yandex.ru")
            put("mail.smtp.port", "587")
            put("mail.smtp.starttls.enable", "true")
            put("mail.smtp.auth", "true")
        }

        val auth = object: Authenticator() {
            override fun getPasswordAuthentication() =
                PasswordAuthentication(from, "Mosfancy9898#")
        }

        val session = Session.getDefaultInstance(properties, auth)

        val message = MimeMessage(session)

        with (message) {
            setFrom(InternetAddress(from))
            for (to in tos) {
                addRecipient(Message.RecipientType.TO, InternetAddress(to))
                subject = "DIAMONDS NFC"
                setContent("<html><body>" +
                        "    <div style=\"display: flex; width:75%; margin: auto; justify-content: space-between; font-family: Tahoma, Geneva, Verdana, sans-serif; font-size: 1rem; color: #666565; font-weight: bold;\">" +
                        "        <div class=\"left\">" +
                        "            <p>Здравствуйте, <span id=\"your__name\">${name}</span>!</p>\n" +
                        "            <p>Мы знаем, что <span id=\"email\">${yourName}</span> мечтает о <span id=\"product__name\">${productName}</span>, поэтому мы решили Вам намекнуть об этом.</p>" +
                        "        </div>" +
                        "        <div class=\"right\">" +
                        "            <img src=\"${img}\" alt=\"image\" width=\"240px\" height=\"360px\">" +
                        "        </div>\n" +
                        "    </div>\n" +
                        "    <div style=\"display: flex; width:75%; margin: auto; justify-content: space-between!important; font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; font-size: 1rem; color: #666565; font-weight: bold;\">" +
                        "        <div class=\"left\">" +
                        "            <p>Дарите тепло своим близким!</p>" +
                        "        </div>\n" +
                        "        <div class=\"right\">" +
                        "            <p><span id=\"product__name\">${productName}</span></p>" +
                        "        </div>" +
                        "    </div>" +
                        "</body>" +
                        "</html>", "text/html; charset=utf-8")
            }
        }

        return message
    }

}