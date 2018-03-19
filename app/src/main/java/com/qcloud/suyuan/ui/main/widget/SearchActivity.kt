package com.qcloud.suyuan.ui.main.widget

import android.content.Context
import android.content.Intent
import android.support.annotation.NonNull
import com.qcloud.suyuan.R
import com.qcloud.suyuan.base.BaseActivity
import com.qcloud.suyuan.enums.SearchType
import com.qcloud.suyuan.ui.main.presenter.impl.SearchPresenterImpl
import com.qcloud.suyuan.ui.main.view.ISearchView
import kotlinx.android.synthetic.main.activity_search.*

/**
 * Description: 搜索
 * Author: gaobaiqiang
 * 2018/3/19 下午9:12.
 */
class SearchActivity: BaseActivity<ISearchView, SearchPresenterImpl>(), ISearchView {
    private var currType = SearchType.searchSuyuan.key

    override val layoutId: Int
        get() = R.layout.activity_search

    override fun initPresenter(): SearchPresenterImpl? {
        return SearchPresenterImpl()
    }

    override fun initViewAndData() {
        currType = intent.getIntExtra("SEARCH_TYPE", SearchType.searchSuyuan.key)
        initToolbar()
    }

    private fun initToolbar() {
        toolbar.setTitleText(SearchType.getTitle(currType))
    }

    companion object {
        fun openActivity(@NonNull context: Context, searchType: Int) {
            var intent = Intent(context, SearchActivity::class.java)
            intent.putExtra("SEARCH_TYPE", searchType)
            context.startActivity(intent)
        }
    }
}