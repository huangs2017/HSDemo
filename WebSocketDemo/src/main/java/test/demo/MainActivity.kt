package test.demo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import test.demo.databinding.ActivityMainBinding
import test.demo.service.MyViewModel
import test.demo.service.MyService
import test.demo.service.ConnectStatus

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val viewModel = MyViewModel
    private lateinit var rvAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvAdapter = MyAdapter(this)
        binding.recyclerView.adapter = rvAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        binding.actionButton.setOnClickListener {
            if (viewModel.status.value != ConnectStatus.CONNECTED) {
                val url = binding.addressEditText.text.toString()
                val intent = Intent(this, MyService::class.java)
                intent.putExtra("serverAddress", url)
                startService(intent)
            } else {
                viewModel.stopByUser.value = true
            }
        }

        binding.sendButton.setOnClickListener {
            binding.editText.text?.toString()?.let {
                viewModel.sendStringData.value = it
                rvAdapter.addContent(BubbleData(SENDER_CLIENT, it))
            }
        }

        viewModel.status.observe(this) { webSocketStatus ->
            webSocketStatus?.let {
                when (it) {
                    ConnectStatus.CONNECTED -> {
                        binding.actionButton.setText("断开")
                        rvAdapter.addContent(BubbleData(SENDER_SYSTEM, "已连接"))
                    }

                    ConnectStatus.CLOSING -> {
                        binding.actionButton.setText("正在断开")
                        rvAdapter.addContent(BubbleData(SENDER_SYSTEM, "正在断开连接"))
                    }

                    ConnectStatus.DISCONNECTED -> {
                        binding.actionButton.setText("连接")
                        rvAdapter.addContent(BubbleData(SENDER_SYSTEM, "连接已断开"))
                    }
                }
            }
        }

        viewModel.receiveStringData.observe(this) { content ->
            rvAdapter.addContent(BubbleData(SENDER_SERVER, content))
        }

    }


    companion object {
        const val SENDER_SYSTEM = 0 // 系统消息
        const val SENDER_SERVER = 1 // 发送方：服务端（远端）
        const val SENDER_CLIENT = 2 // 发送方：客户端（本机）
    }

}