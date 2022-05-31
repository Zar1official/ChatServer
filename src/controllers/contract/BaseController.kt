package com.chat_server.controllers.contract

import com.chat_server.data.repository.contract.Repository

abstract class BaseController(protected val repository: Repository)