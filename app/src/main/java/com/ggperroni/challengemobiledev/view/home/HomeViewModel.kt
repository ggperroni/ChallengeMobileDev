package com.ggperroni.challengemobiledev.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ggperroni.challengemobiledev.data.remote.TreeItem
import com.ggperroni.challengemobiledev.data.remote.TreeItemResponse
import com.ggperroni.challengemobiledev.data.tree.TreeRepository
import com.ggperroni.challengemobiledev.domain.tree.GetTreeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.handleCoroutineException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTreeUseCase: GetTreeUseCase,
    private val repository: TreeRepository
) : ViewModel() {

    private val _treeState = MutableStateFlow(emptyList<TreeItem>())
    val treeState = _treeState.asStateFlow()

    init {
        loadTree()
    }

    private fun loadTree() {
        viewModelScope.launch {
            _treeState.value = getTreeUseCase()
        }
    }

    fun updateTree(id: Int, name: String) {
        viewModelScope.launch {
            repository.updateTree(id, name)
            _treeState.value = getTreeUseCase()
        }
    }
}