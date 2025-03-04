package com.aslanjavasky.shawarmadelviry.conf

import com.aslanjavasky.shawarmadelviry.domain.model.MenuItem
import com.aslanjavasky.shawarmadelviry.domain.model.MenuSection
import com.aslanjavasky.shawarmadelviry.domain.repo.MenuItemRepo
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Component
class ApplicationStartupRunner(
    @Qualifier("MenuItemRepoAdapter_JPA") private val menuItemRepo: MenuItemRepo
) : CommandLineRunner {

    override fun run(vararg args: String?) {

//        menuItemRepo.deleteAll()

        menuItemRepo.saveMenuItem(
            MenuItem(1L, "Гиро в лаваше L", MenuSection.MAIN_MENU, BigDecimal.valueOf(240))
        )
        menuItemRepo.saveMenuItem(
            MenuItem(2L, "Гиро в лаваше XL", MenuSection.MAIN_MENU, BigDecimal.valueOf(290))
        )
        menuItemRepo.saveMenuItem(
            MenuItem(3L, "Гиро в лепешке", MenuSection.MAIN_MENU, BigDecimal.valueOf(240))
        )
        menuItemRepo.saveMenuItem(
            MenuItem(4L, "Гиро в пите", MenuSection.MAIN_MENU, BigDecimal.valueOf(240))
        )
        menuItemRepo.saveMenuItem(
            MenuItem(5L, "Люля кебаб на углях в лаваше", MenuSection.MAIN_MENU, BigDecimal.valueOf(330))
        )
        menuItemRepo.saveMenuItem(
            MenuItem(6L, "Люля на углях в лепешке", MenuSection.MAIN_MENU, BigDecimal.valueOf(330))
        )
        menuItemRepo.saveMenuItem(
            MenuItem(7L, "Чизбургер куринный", MenuSection.MAIN_MENU, BigDecimal.valueOf(230))
        )
        menuItemRepo.saveMenuItem(
            MenuItem(8L, "Хот-Дог", MenuSection.MAIN_MENU, BigDecimal.valueOf(150))
        )
        menuItemRepo.saveMenuItem(
            MenuItem(9L, "Блэкбургер", MenuSection.MAIN_MENU, BigDecimal.valueOf(230))
        )

        menuItemRepo.saveMenuItem(
            MenuItem(10L, "Фри L", MenuSection.ZAKUSKI, BigDecimal.valueOf(120))
        )
        menuItemRepo.saveMenuItem(
            MenuItem(11L, "Фри XL", MenuSection.ZAKUSKI, BigDecimal.valueOf(150))
        )
        menuItemRepo.saveMenuItem(
            MenuItem(12L, "По-деревенски", MenuSection.ZAKUSKI, BigDecimal.valueOf(150))
        )
        menuItemRepo.saveMenuItem(
            MenuItem(13L, "Наггетсы", MenuSection.ZAKUSKI, BigDecimal.valueOf(150))
        )
        menuItemRepo.saveMenuItem(
            MenuItem(14L, "Французский Хот-Дог", MenuSection.ZAKUSKI, BigDecimal.valueOf(150))
        )

        menuItemRepo.saveMenuItem(
            MenuItem(15L, "Халапеньо", MenuSection.DOBAVKI, BigDecimal.valueOf(40))
        )
        menuItemRepo.saveMenuItem(
            MenuItem(16L, "Сыр", MenuSection.DOBAVKI, BigDecimal.valueOf(40))
        )
        menuItemRepo.saveMenuItem(
            MenuItem(17L, "Фирменный от Шефа", MenuSection.SAUCE, BigDecimal.valueOf(40))
        )
        menuItemRepo.saveMenuItem(
            MenuItem(18L, "Томатный", MenuSection.SAUCE, BigDecimal.valueOf(40))
        )
        menuItemRepo.saveMenuItem(
            MenuItem(19L, "Барбекю", MenuSection.SAUCE, BigDecimal.valueOf(40))
        )
        menuItemRepo.saveMenuItem(
            MenuItem(20L, "Сырный", MenuSection.SAUCE, BigDecimal.valueOf(40))
        )
        menuItemRepo.saveMenuItem(
            MenuItem(21L, "Чесночный", MenuSection.SAUCE, BigDecimal.valueOf(40))
        )
        menuItemRepo.saveMenuItem(
            MenuItem(22L, "Кисло-Сладкий", MenuSection.SAUCE, BigDecimal.valueOf(40))
        )
        menuItemRepo.saveMenuItem(
            MenuItem(23L, "Шаурма L", MenuSection.MAIN_MENU, BigDecimal.valueOf(240))
        )
        menuItemRepo.saveMenuItem(
            MenuItem(24L, "Шаурма XL", MenuSection.MAIN_MENU, BigDecimal.valueOf(290))
        )
    }
}