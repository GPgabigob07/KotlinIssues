package com.nta.githubkotlinissueapp.ui.fragment.issueDetails

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.style.BackgroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.buildSpannedString
import androidx.fragment.app.Fragment
import com.google.android.material.transition.MaterialContainerTransform
import com.nta.githubkotlinissueapp.R
import com.nta.githubkotlinissueapp.databinding.FragmentIssueDetailsBinding
import com.nta.githubkotlinissueapp.ui.viewmodel.IssuesViewModel
import org.koin.android.viewmodel.ext.android.sharedViewModel
import java.text.SimpleDateFormat
import java.util.*

class IssueDetailsFragment : Fragment() {

    private lateinit var binder: FragmentIssueDetailsBinding

    private val vm: IssuesViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.nav_host_fragment
            duration = 500
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(Color.TRANSPARENT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentIssueDetailsBinding.inflate(inflater, container, false)
            .also { binder = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(vm.inDetails!!) {
            with(binder.also { it.issue = this }) {

                content.text = buildSpannedString {
                    val parts = body?.split("```") ?: listOf()

                    //Highlights text if contains some sort of 'code'
                    if (parts.isNotEmpty()) {
                        if (parts.size > 2) {
                            parts.forEachIndexed { index, s ->
                                if ((index + 1) % 2 == 0) {
                                    val lastSize = length
                                    append(
                                        s.trim(),
                                        BackgroundColorSpan(
                                            ResourcesCompat.getColor(
                                                resources,
                                                R.color.grayed,
                                                null
                                            )
                                        ),
                                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                                    )
                                    setSpan(
                                        RelativeSizeSpan(0.85f),
                                        lastSize,
                                        length,
                                        Spannable.SPAN_INCLUSIVE_EXCLUSIVE
                                    )
                                } else {
                                    append(s)
                                }
                            }

                        } else {
                            append(parts.fold("") { a, b ->
                                a + "\n" + b
                            })
                        }
                    }

                }
                statusContainer.setCardBackgroundColor(getColor(requireContext()))

                date.text =
                    SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.getDefault()).format(
                        updatedAt ?: Date()
                    )

                openInGit.setOnClickListener {
                    startActivity(
                        Intent.createChooser(
                            Intent(Intent.ACTION_VIEW, Uri.parse(htmlURL)),
                            getString(R.string.see_on_github)
                        )
                    )
                }
            }
        }
    }
}