package com.miraz.navbarbottom


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.miraz.navbarbottom.databinding.FragmentSomeBinding


/**
 * A simple [Fragment] subclass.
 * Use the [SomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SomeFragment : Fragment() {

    private var _binding: FragmentSomeBinding? = null
    private val binding get() = _binding!!

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = requireArguments().getString(ARG_PARAM1)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentText.text = arguments?.getString(ARG_PARAM1) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        inflater!!.inflate(R.layout.fragment_some, container, false)

        _binding = FragmentSomeBinding.inflate(inflater, container, false)
        return binding.root

    }


    companion object {
        private val ARG_PARAM1 = "param1"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String): SomeFragment {
            val fragment = SomeFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            fragment.arguments = args
            return fragment
        }
    }

}
