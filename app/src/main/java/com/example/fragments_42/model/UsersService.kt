package com.example.fragments_42.model

import com.github.javafaker.Faker

typealias UsersListener = (users: List<User>) -> Unit

class UsersService {
    private var users = mutableListOf<User>()
    private val listeners = mutableSetOf<UsersListener>()

    init {
        val faker = Faker.instance()
        IMAGES.shuffle()
        users = (1..11).map {
            User(
                id = it.toLong(),
                image = IMAGES[it % IMAGES.size],
                name = faker.name().name(),
                surname = faker.name().lastName(),
                phone = faker.phoneNumber().phoneNumber()
            )
        }.toMutableList()
    }

    fun addListener(listener: UsersListener) {
        listeners.add(listener)
        listener.invoke(users.toList())
    }

    fun removeListener(listener: UsersListener) {
        listeners.remove(listener)
    }

    private fun notifyChanges() {
        listeners.forEach { it.invoke(users.toList()) }
    }

    fun updateUser(updatedUser: User) {
        users = users.map { if (it.id == updatedUser.id) updatedUser else it }.toMutableList()
        notifyChanges()
    }

    companion object {
        private val IMAGES = mutableListOf(
            "https://img.7ya.ru/pub/img/7611/134192059.jpg",
            "https://i.pinimg.com/originals/a3/a1/81/a3a181109ddb1aedbd808256706b5bf6.jpg",
            "https://classpic.ru/wp-content/uploads/2018/06/32187/smejushhijsja-muzhchina-na-fone-morja-s-ineem-v-borode.jpg",
            "https://i.pinimg.com/originals/7f/77/62/7f7762719fc29fa50e3730b673ed73e6.jpg",
            "https://get.pxhere.com/photo/person-people-portrait-facial-expression-hairstyle-smile-emotion-portrait-photography-134689.jpg",
            "https://mykaleidoscope.ru/x/uploads/posts/2022-10/1666247448_53-mykaleidoscope-ru-p-radostnii-muzhchina-vkontakte-65.jpg",
            "https://get.pxhere.com/photo/tree-grass-person-blur-girl-woman-photography-sunlight-flower-cute-summer-portrait-model-spring-autumn-fashion-lady-bride-season-smiling-smile-close-up-trees-outdoors-focus-dress-happy-happiness-photograph-beauty-joy-beautiful-pretty-feeling-photo-shoot-portrait-photography-1267748.jpg",
            "http://psychospace.ru/wp-content/uploads/2021/03/veselyj-chelovek.jpg",
            "https://zaim-bistro.ru/wp-content/uploads/2017/02/otzyvy-pro-moneyveo.jpg",
            "https://img.wprost.pl/_thumb/4f/7e/47e37cf576ef8c436ce028f3d29a.jpeg"
        )
    }
}