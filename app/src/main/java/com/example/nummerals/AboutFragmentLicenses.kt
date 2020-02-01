import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.nummerals.R
import com.example.nummerals.databinding.FragmentAboutBinding
import com.example.nummerals.databinding.FragmentAboutLicensesBinding
import com.example.nummerals.viewModels.ExerciseViewModel

class AboutFragmentLicenses : Fragment() {
    private lateinit var mBinding: FragmentAboutLicensesBinding
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

        var licenseText =
            "<pre>Copyright 2019 ООО \"Локус\"\n" +
                    "\n" +
                    "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                    "you may not use this file except in compliance with the License.\n" +
                    "You may obtain a copy of the License at\n" +
                    "\n" +
                    "    http://www.apache.org/licenses/LICENSE-2.0\n" +
                    "\n" +
                    "Unless required by applicable law or agreed to in writing, software\n" +
                    "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                    "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                    "See the License for the specific language governing permissions and\n" +
                    "limitations under the License.\n" +
                    "\n" +
                    "<strong><p>Application developers:</p></strong>\n" +
                    "<strong><p>Vladimir Khomin</p></strong>\n" +
                    "<strong><p>Roman Khikhin</p></strong>\n" +
                    "<p>Also used components from open source projects</p>\n" +
                    "</pre>"

        mBinding.licenseText1.text = HtmlCompat.fromHtml(licenseText, 0);

        licenseText = "<pre><code>\n" +
                "Copyright 2018 Erdem Orman\n" +
                "\n" +
                "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "you may not use this file except in compliance with the License.\n" +
                "You may obtain a copy of the License at\n" +
                "\n" +
                "    http://www.apache.org/licenses/LICENSE-2.0\n" +
                "\n" +
                "Unless required by applicable law or agreed to in writing, software\n" +
                "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "See the License for the specific language governing permissions and\n" +
                "limitations under the License.\n" +
                "\n" +
                "</code></pre>"

        mBinding.licenseText2.text = HtmlCompat.fromHtml(licenseText, 0);


        licenseText = "<p style=\"text-align:center\">\n" +
                "<strong>Apache License<br><br>Version 2.0, January 2004<br><br></strong>\n" +
                "<strong><a href=\"http://www.apache.org/licenses/\">http://www.apache.org/licenses/</a></strong>\n" +
                "</p>\n" +
                "\n" +
                "<p>TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION</p>\n" +
                "\n" +
                "<p><strong><a name=\"definitions\">1. Definitions</a></strong>.</p>\n" +
                "\n" +
                "<div style=\"margin-left: 1em;\">\n" +
                "\n" +
                "  <p>\"<strong>License</strong>\" shall mean the terms and conditions for use, reproduction, and\n" +
                "distribution as defined by Sections 1 through 9 of this document.</p>\n" +
                "\n" +
                "  <p>\"<strong>Licensor</strong>\" shall mean the copyright owner or entity authorized by the\n" +
                "copyright owner that is granting the License.</p>\n" +
                "\n" +
                "  <p>\"<strong>Legal Entity</strong>\" shall mean the union of the acting entity and all other\n" +
                "entities that control, are controlled by, or are under common control with\n" +
                "that entity. For the purposes of this definition, \"<strong>control</strong>\" means (i) the\n" +
                "power, direct or indirect, to cause the direction or management of such\n" +
                "entity, whether by contract or otherwise, or (ii) ownership of fifty\n" +
                "percent (50%) or more of the outstanding shares, or (iii) beneficial\n" +
                "ownership of such entity.</p>\n" +
                "\n" +
                "  <p>\"<strong>You</strong>\" (or \"<strong>Your</strong>\") shall mean an individual or Legal Entity exercising\n" +
                "permissions granted by this License.</p>\n" +
                "\n" +
                "  <p>\"<strong>Source</strong>\" form shall mean the preferred form for making modifications,\n" +
                "including but not limited to software source code, documentation source,\n" +
                "and configuration files.</p>\n" +
                "\n" +
                "  <p>\"<strong>Object</strong>\" form shall mean any form resulting from mechanical transformation\n" +
                "or translation of a Source form, including but not limited to compiled\n" +
                "object code, generated documentation, and conversions to other media types.</p>\n" +
                "\n" +
                "  <p>\"<strong>Work</strong>\" shall mean the work of authorship, whether in Source or Object form,\n" +
                "made available under the License, as indicated by a copyright notice that\n" +
                "is included in or attached to the work (an locuscom is provided in the\n" +
                "Appendix below).</p>\n" +
                "\n" +
                "  <p>\"<strong>Derivative Works</strong>\" shall mean any work, whether in Source or Object form,\n" +
                "that is based on (or derived from) the Work and for which the editorial\n" +
                "revisions, annotations, elaborations, or other modifications represent, as\n" +
                "a whole, an original work of authorship. For the purposes of this License,\n" +
                "Derivative Works shall not include works that remain separable from, or\n" +
                "merely link (or bind by name) to the interfaces of, the Work and Derivative\n" +
                "Works thereof.</p>\n" +
                "\n" +
                "  <p>\"<strong>Contribution</strong>\" shall mean any work of authorship, including the original\n" +
                "version of the Work and any modifications or additions to that Work or\n" +
                "Derivative Works thereof, that is intentionally submitted to Licensor for\n" +
                "inclusion in the Work by the copyright owner or by an individual or Legal\n" +
                "Entity authorized to submit on behalf of the copyright owner. For the\n" +
                "purposes of this definition, \"<strong>submitted</strong>\" means any form of electronic,\n" +
                "verbal, or written communication sent to the Licensor or its\n" +
                "representatives, including but not limited to communication on electronic\n" +
                "mailing lists, source code control systems, and issue tracking systems that\n" +
                "are managed by, or on behalf of, the Licensor for the purpose of discussing\n" +
                "and improving the Work, but excluding communication that is conspicuously\n" +
                "marked or otherwise designated in writing by the copyright owner as \"<strong>Not a\n" +
                "Contribution.</strong>\"</p>\n" +
                "\n" +
                "  <p>\"<strong>Contributor</strong>\" shall mean Licensor and any individual or Legal Entity on\n" +
                "behalf of whom a Contribution has been received by Licensor and\n" +
                "subsequently incorporated within the Work.</p>\n" +
                "\n" +
                "</div>\n" +
                "\n" +
                "<p><strong><a name=\"copyright\">2. Grant of Copyright License</a></strong>. Subject to the\n" +
                "terms and conditions of this License, each Contributor hereby grants to You\n" +
                "a perpetual, worldwide, non-exclusive, no-charge, royalty-free, irrevocable\n" +
                "copyright license to reproduce, prepare Derivative Works of, publicly\n" +
                "display, publicly perform, sublicense, and distribute the Work and such\n" +
                "Derivative Works in Source or Object form.</p>\n" +
                "\n" +
                "<p><strong><a name=\"patent\">3. Grant of Patent License</a></strong>. Subject to the terms\n" +
                "and conditions of this License, each Contributor hereby grants to You a\n" +
                "perpetual, worldwide, non-exclusive, no-charge, royalty-free, irrevocable\n" +
                "(except as stated in this section) patent license to make, have made, use,\n" +
                "offer to sell, sell, import, and otherwise transfer the Work, where such\n" +
                "license applies only to those patent claims licensable by such Contributor\n" +
                "that are necessarily infringed by their Contribution(s) alone or by\n" +
                "combination of their Contribution(s) with the Work to which such\n" +
                "Contribution(s) was submitted. If You institute patent litigation against\n" +
                "any entity (including a cross-claim or counterclaim in a lawsuit) alleging\n" +
                "that the Work or a Contribution incorporated within the Work constitutes\n" +
                "direct or contributory patent infringement, then any patent licenses\n" +
                "granted to You under this License for that Work shall terminate as of the\n" +
                "date such litigation is filed.</p>\n" +
                "\n" +
                "<p><strong><a name=\"redistribution\">4. Redistribution</a></strong>. You may reproduce and\n" +
                "distribute copies of the Work or Derivative Works thereof in any medium,\n" +
                "with or without modifications, and in Source or Object form, provided that\n" +
                "You meet the following conditions:</p>\n" +
                "\n" +
                "<ol style=\"list-style: lower-latin;\">\n" +
                "<li>You must give any other recipients of the Work or Derivative Works a\n" +
                "copy of this License; and</li>\n" +
                "\n" +
                "<li>You must cause any modified files to carry prominent notices stating\n" +
                "that You changed the files; and</li>\n" +
                "\n" +
                "<li>You must retain, in the Source form of any Derivative Works that You\n" +
                "distribute, all copyright, patent, trademark, and attribution notices from\n" +
                "the Source form of the Work, excluding those notices that do not pertain to\n" +
                "any part of the Derivative Works; and</li>\n" +
                "\n" +
                "<li>If the Work includes a \"<strong>NOTICE</strong>\" text file as part of its distribution,\n" +
                "then any Derivative Works that You distribute must include a readable copy\n" +
                "of the attribution notices contained within such NOTICE file, excluding\n" +
                "those notices that do not pertain to any part of the Derivative Works, in\n" +
                "at least one of the following places: within a NOTICE text file distributed\n" +
                "as part of the Derivative Works; within the Source form or documentation,\n" +
                "if provided along with the Derivative Works; or, within a display generated\n" +
                "by the Derivative Works, if and wherever such third-party notices normally\n" +
                "appear. The contents of the NOTICE file are for informational purposes only\n" +
                "and do not modify the License. You may add Your own attribution notices\n" +
                "within Derivative Works that You distribute, alongside or as an addendum to\n" +
                "the NOTICE text from the Work, provided that such additional attribution\n" +
                "notices cannot be construed as modifying the License.\n" +
                "<br>\n" +
                "<br>\n" +
                "You may add Your own copyright statement to Your modifications and may\n" +
                "provide additional or different license terms and conditions for use,\n" +
                "reproduction, or distribution of Your modifications, or for any such\n" +
                "Derivative Works as a whole, provided Your use, reproduction, and\n" +
                "distribution of the Work otherwise complies with the conditions stated in\n" +
                "this License.\n" +
                "</li>\n" +
                "\n" +
                "</ol>\n" +
                "\n" +
                "<p><strong><a name=\"contributions\">5. Submission of Contributions</a></strong>. Unless You\n" +
                "explicitly state otherwise, any Contribution intentionally submitted for\n" +
                "inclusion in the Work by You to the Licensor shall be under the terms and\n" +
                "conditions of this License, without any additional terms or conditions.\n" +
                "Notwithstanding the above, nothing herein shall supersede or modify the\n" +
                "terms of any separate license agreement you may have executed with Licensor\n" +
                "regarding such Contributions.</p>\n" +
                "\n" +
                "<p><strong><a name=\"trademarks\">6. Trademarks</a></strong>. This License does not grant\n" +
                "permission to use the trade names, trademarks, service marks, or product\n" +
                "names of the Licensor, except as required for reasonable and customary use\n" +
                "in describing the origin of the Work and reproducing the content of the\n" +
                "NOTICE file.</p>\n" +
                "\n" +
                "<p><strong><a name=\"no-warranty\">7. Disclaimer of Warranty</a></strong>. Unless required by\n" +
                "applicable law or agreed to in writing, Licensor provides the Work (and\n" +
                "each Contributor provides its Contributions) on an \"AS IS\" BASIS, WITHOUT\n" +
                "WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied, including,\n" +
                "without limitation, any warranties or conditions of TITLE,\n" +
                "NON-INFRINGEMENT, MERCHANTABILITY, or FITNESS FOR A PARTICULAR PURPOSE. You\n" +
                "are solely responsible for determining the appropriateness of using or\n" +
                "redistributing the Work and assume any risks associated with Your exercise\n" +
                "of permissions under this License.</p>\n" +
                "\n" +
                "<p><strong><a name=\"no-liability\">8. Limitation of Liability</a></strong>. In no event and\n" +
                "under no legal theory, whether in tort (including negligence), contract, or\n" +
                "otherwise, unless required by applicable law (such as deliberate and\n" +
                "grossly negligent acts) or agreed to in writing, shall any Contributor be\n" +
                "liable to You for damages, including any direct, indirect, special,\n" +
                "incidental, or consequential damages of any character arising as a result\n" +
                "of this License or out of the use or inability to use the Work (including\n" +
                "but not limited to damages for loss of goodwill, work stoppage, computer\n" +
                "failure or malfunction, or any and all other commercial damages or losses),\n" +
                "even if such Contributor has been advised of the possibility of such\n" +
                "damages.</p>\n" +
                "\n" +
                "<p><strong><a name=\"additional\">9. Accepting Warranty or Additional Liability</a></strong>.\n" +
                "While redistributing the Work or Derivative Works thereof, You may choose\n" +
                "to offer, and charge a fee for, acceptance of support, warranty, indemnity,\n" +
                "or other liability obligations and/or rights consistent with this License.\n" +
                "However, in accepting such obligations, You may act only on Your own behalf\n" +
                "and on Your sole responsibility, not on behalf of any other Contributor,\n" +
                "and only if You agree to indemnify, defend, and hold each Contributor\n" +
                "harmless for any liability incurred by, or claims asserted against, such\n" +
                "Contributor by reason of your accepting any such warranty or additional\n" +
                "liability.</p>\n" +
                "\n" +
                "<p>END OF TERMS AND CONDITIONS</p>"

        mBinding.licenseText3.text = HtmlCompat.fromHtml(licenseText, 0);

        return mBinding.root
    }
}
