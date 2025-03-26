package com.tongji.xiaohashu.note.biz.constant

object RedisKeyConstants {
    /**
     * 笔记详情 KEY 前缀
     */
    private const val NOTE_DETAIL_KEY: String = "note:detail:"


    /**
     * 构建完整的笔记详情 KEY
     * @param noteId
     * @return
     */
    @JvmStatic
    fun buildNoteDetailKey(noteId: Long) = "$NOTE_DETAIL_KEY:$noteId"
}