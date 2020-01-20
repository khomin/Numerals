import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.nummerals.R
import com.example.nummerals.databinding.FragmentAboutBinding
import com.example.nummerals.viewModels.ExerciseViewModel

class AboutFragmentLicenses : Fragment() {
    private lateinit var mBinding: FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_about_licenses, container, false)
        mBinding.lifecycleOwner = this

        activity?.let {
            mBinding.model = ViewModelProviders.of(it).get(ExerciseViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        return mBinding.root
    }
}
