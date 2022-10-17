package com.learn.vocabulary.ui.learn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.learn.vocabulary.R
import com.learn.vocabulary.databinding.FragmentLearnBinding
import com.learn.vocabulary.ui.MainActivity
import com.learn.vocabulary.ui.learn.adapter.LearnListAdapter
import com.learn.vocabulary.viewmodel.VocabularyViewModel

class LearnFragment : Fragment() {

    private var _binding: FragmentLearnBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: VocabularyViewModel
    lateinit var learnListAdapter: LearnListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()


        viewModel = ViewModelProvider(requireActivity()).get(VocabularyViewModel::class.java)
        viewModel.fetchCategoriesFromRemoteApi()

        viewModel.categories.observe(requireActivity()) {
            learnListAdapter.submitList(it.cats)
        }

        binding.toolbar.title.text = "Category"
        binding.toolbar.leftIcon.visibility = View.GONE

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLearnBinding.inflate(inflater, container, false)

        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initAdapter() {
        learnListAdapter = LearnListAdapter {
            findNavController().navigate(
                R.id.action_learnFragment_to_wordDetailFragment,
                bundleOf("word_detail" to it)
            )
        }

        binding.learnRec.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.learnRec.adapter = learnListAdapter

    }
}