package me.lab.happyprimo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import me.lab.happyprimo.R
import me.lab.happyprimo.data.models.FeedResponseModel
import me.lab.happyprimo.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HomeFragment : Fragment() ,FeedAdapter.OnItemClickListener{

    private var _binding: FragmentHomeBinding? = null

    private lateinit var feedAdapter: FeedAdapter

    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModel { parametersOf(requireContext(), viewLifecycleOwner.lifecycleScope) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView: TextView = binding.textMeduimName
        homeViewModel.getDataFeed()
        homeViewModel.apply {
            text.observe(viewLifecycleOwner) {
                textView.text = it
            }
            meduimProfileUrl.observe(viewLifecycleOwner) {
                Glide.with(requireContext())
                    .load(it)
                    .into(binding.imageViewMeduimProfile)
            }
            showProgressbar.observe(viewLifecycleOwner) { isVisible ->
                binding.animationView.visibility = if (isVisible) VISIBLE else GONE
                binding.recyclerViewFeed.visibility = if (isVisible) GONE else VISIBLE
            }
            feedData.observe(viewLifecycleOwner){
              val data :List<FeedResponseModel> = it
                feedAdapter = FeedAdapter(data.first().item,this@HomeFragment)
                binding.recyclerViewFeed.apply {
                    adapter = feedAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                }

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
           activity?.finish()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(item: FeedResponseModel.Item) {
        val bundle = Bundle().apply {
            putString("url", item.title!!.link!!.guid!!.guidData)
        }
        findNavController().navigate(R.id.action_fragment_home_to_fragment_detail, bundle)
    }
}