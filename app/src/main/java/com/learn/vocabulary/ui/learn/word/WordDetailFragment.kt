package com.learn.vocabulary.ui.learn.word

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.toColor
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.room.Room
import com.learn.vocabulary.R
import com.learn.vocabulary.data.local.AppDatabase
import com.learn.vocabulary.databinding.FragmentWordDetailBinding
import com.learn.vocabulary.model.Word
import com.learn.vocabulary.ui.learn.word.adapter.WordDetailItemAdapter
import com.learn.vocabulary.util.Constant.USER_DATABASE
import com.learn.vocabulary.viewmodel.VocabularyViewModel


class WordDetailFragment : Fragment() {
    private var _binding: FragmentWordDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var snapHelper: SnapHelper
    private var list: ArrayList<Word> = arrayListOf()
    var pos = 0
    var cat_id = -1
    val args: WordDetailFragmentArgs by navArgs()


    private val userDB: AppDatabase by lazy {
        Room.databaseBuilder(requireActivity(), AppDatabase::class.java, USER_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    private lateinit var viewModel: VocabularyViewModel
    private lateinit var wordDetailAdapter: WordDetailItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(VocabularyViewModel::class.java)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        snapHelper = PagerSnapHelper()
        initAdapter()

        binding.toolbar.title.text = "${args.wordDetail?.name}"
        binding.toolbar.leftIcon.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.saveBtn.visibility = View.GONE


        cat_id = args.wordDetail?.id ?: -1

        viewModel.fetchWordsByCategoriesFromRemoteApi(cat_id)


        viewModel.wordsByCategories.observe(requireActivity()) {
            list.clear()
            list.addAll(it.words)
            wordDetailAdapter.submitList(null)
            wordDetailAdapter.submitList(list)
            wordDetailAdapter.notifyDataSetChanged()
        }

        binding.finishBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.nextBtn.setOnClickListener {
            pos++
            buttonCheck()
            (binding.wordDetailRec.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                pos, 1
            )
        }

        binding.saveBtn.setOnClickListener {
            pos--
            buttonCheck()
            (binding.wordDetailRec.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(
                pos,
                1
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWordDetailBinding.inflate(inflater, container, false)


        val view = binding.root
        return view
    }

    private fun buttonCheck() {
        if (pos > 0) {
            binding.saveBtn.visibility = View.VISIBLE
        } else {
            binding.saveBtn.visibility = View.GONE
        }
        if (pos == list.size - 1) {
            binding.nextBtn.visibility = View.GONE
            binding.finishBtn.visibility = View.VISIBLE
        } else {
            binding.nextBtn.visibility = View.VISIBLE
            binding.finishBtn.visibility = View.GONE
        }
    }

    private fun initAdapter() {
        wordDetailAdapter = WordDetailItemAdapter(characterClickCallback = {
            Toast.makeText(requireActivity(), it?.word, Toast.LENGTH_SHORT).show()


        }, favClick = {

        })

        snapHelper.attachToRecyclerView(binding.wordDetailRec)
        binding.wordDetailRec.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.wordDetailRec.adapter = wordDetailAdapter

        binding.wordDetailRec.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                    val centerView = snapHelper.findSnapView(binding.wordDetailRec.layoutManager)
                    val position: Int =
                        (binding.wordDetailRec.layoutManager as LinearLayoutManager).getPosition(
                            centerView!!
                        )

                    pos = position
                    buttonCheck()
                }
            }
        })
    }
}