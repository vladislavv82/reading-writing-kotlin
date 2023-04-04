import java.io.*
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.util.*

fun main(args: Array<String>) {
        //Здесь будем использовать 2-ой способ чтения и записи
        val source = "C:\\Users\\Владислав\\Desktop\\KotlinLab2\\src\\main\\Task\\task.txt"
        val folder = "C:\\Users\\Владислав\\Desktop\\KotlinLab2\\src\\main\\Task\\resultat.txt"

        println("Вы попали в меню, выберите действие:")
        println("1) С помощью Input- и OutputStream\n" +
                "2) С помощью встроенных методов Kotlin\n" +
                "3) С помощью  BufferedReader и BufferedWriter")
        println("Введите номер: ")

        val scanner = Scanner(System.`in`)
        val x = scanner.nextInt()

        when(x){
                1 -> InputOutputStreamKotlin(source, folder)
                2 -> KotlinMethod(source, folder)
                3 -> BufferedKotlin(source,folder)
        }



        // Создание новой папки
        val folderResult = File("C:\\Users\\Владислав\\Desktop\\KotlinLab2\\src\\main\\folderResult")

        // Проверка на существование папки
        if (!folderResult.exists()) {
                folderResult.mkdirs() //для того что бы создать папку
                println("Создал новую папку folderResult")
        } else {
                println("Папка уже существует, перемещаю в директорию выше")
        }

        // Теперь перелаживаем файл result в новую папку folderResult
        // Можно функцию переноса вынести отдельно
        try { moveFile("C:\\Users\\Владислав\\Desktop\\KotlinLab2\\src\\main\\Task\\resultat.txt",
                        "C:\\Users\\Владислав\\Desktop\\KotlinLab2\\src\\main\\folderResult")
                println("Файл перенесен в другую папку")
        }catch (ex: IOException){
                ex.printStackTrace()
        }

        // а можно использовать прям тут
        //Files.move(outputFile.toPath(), folderResult.toPath(), StandardCopyOption.REPLACE_EXISTING)

        // Теперь будем переименовывать файл, для этого у нас есть функция, которая будет принимать старое и новое название
        try {
                renameFile("C:\\Users\\Владислав\\Desktop\\KotlinLab2\\src\\main\\folderResult\\resultat.txt",
                        "C:\\Users\\Владислав\\Desktop\\KotlinLab2\\src\\main\\folderResult\\final_resultat.txt")
        }catch (ex: IOException){
                ex.printStackTrace()
        }
        println("End")

}
//Чтение и запись 1-м способом
fun InputOutputStreamKotlin(source: String, folder: String) {

        val inputFile = File(source)
        val outputFile = File(folder)

        var i: Int
        val builder = StringBuilder()
        val inputStream = InputStreamReader(inputFile.inputStream(), "UTF-8")

        while (inputStream.read().also { i = it } != -1) {
                builder.append(i.toChar())
        }
        val finally = builder.trim().replace(Regex("( )+"), " ")

        val outputStream = OutputStreamWriter(outputFile.outputStream(), "UTF-8")
        outputStream.write(finally)

        //закрываем потоки
        inputStream.close()
        outputStream.close()
}
//Чтение и запись 2-м способом
fun KotlinMethod(source: String, folder: String){

        val inputFile = File(source)
        val outputFile = File(folder)
        // Чтение содержимого файла в строку
        val stringList = inputFile.readLines() //считываем построчно
        println("Папка прочитана")

        // Удаление пробелов из строки
        val resultString = stringList.map { it.trim().replace(Regex("\\s+"), " ") }
        println("Привожу файл к нужному результату")
        // trim убирает пробелы в начале и в конце
        //replace заменяет Regex регулярное выражение которое видит 2 и больше пробела заменяя их на 1


        // Запись результата в файл
        outputFile.writeText(resultString.joinToString("\n")) //после каждой строки \n
        println("Создаю файл result.txt")
}

//Чтение и запись 3-м способом
fun BufferedKotlin(source: String, folder: String) {

        val sourceFile = File(source)
        val folderFile = File(folder)

        // Создаем BufferedReader и BufferedWriter для чтения и записи файлов
        val reader = BufferedReader(FileReader(sourceFile))
        val writer = BufferedWriter(FileWriter(folderFile))

        // Считываем файл по строкам и удаляем повторяющиеся пробелы
        var line = reader.readLine()
        while (line != null) {
                val editedLine = line.trim().replace("\\s+".toRegex(), " ")
                writer.write(editedLine)
                writer.newLine()
                line = reader.readLine()
        }

        // Закрываем все stream
        reader.close()
        writer.close()
}

//Функция для переноса файлов в другую папку
fun moveFile(source: String, folder: String) {
        val source = File(source)
        val folder = File(folder, source.name)

        Files.move(source.toPath(), folder.toPath(), StandardCopyOption.REPLACE_EXISTING)
}

//Функция для переименования файла
fun renameFile(old: String, new: String){
        val old = File(old)
        val new = File(new)

        if (old.exists()) {
                old.renameTo(new)
                println("Файл успешно переименован.")
        } else {
                println("Файл не существует.")
        }

}