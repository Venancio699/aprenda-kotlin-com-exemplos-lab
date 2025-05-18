import java.time.LocalDate

// Enum para representar o nível da formação
enum class Nivel {
    JUNIOR, PLENO, SENIOR
}

// Representa o conteúdo educacional (curso ou mentoria)
data class ConteudoEducacional(
    val nome: String,
    val duracao: Int = 60,
    val pontuacao: Int = 10
)

// Representa o usuário (aluno)
class Usuario(val nome: String) {
    val conteudosConcluidos = mutableListOf<ConteudoEducacional>()
    val conteudosPendentes = mutableListOf<ConteudoEducacional>()
    var pontuacaoTotal: Int = 0
        private set

    fun progredir() {
        if (conteudosPendentes.isNotEmpty()) {
            val conteudo = conteudosPendentes.removeAt(0)
            conteudosConcluidos.add(conteudo)
            pontuacaoTotal += conteudo.pontuacao
            println("✅ $nome concluiu '${conteudo.nome}' (+${conteudo.pontuacao} pts)")
        } else {
            println("⚠️ $nome não possui conteúdos pendentes.")
        }
    }

    fun exibirProgresso() {
        println("\n📊 Progresso de $nome:")
        println("Pontos totais: $pontuacaoTotal")
        println("Concluídos:")
        conteudosConcluidos.forEach { println(" - ${it.nome}") }
        println("Pendentes:")
        conteudosPendentes.forEach { println(" - ${it.nome}") }
    }
}

// Representa a formação da DIO Expert
data class Formacao(
    val nome: String,
    val nivel: Nivel,
    val conteudos: List<ConteudoEducacional>
) {
    private val inscritos = mutableListOf<Usuario>()

    fun matricular(usuario: Usuario) {
        inscritos.add(usuario)
        usuario.conteudosPendentes.addAll(conteudos)
        println("🎓 ${usuario.nome} foi matriculado na formação '$nome' [${nivel.name}]")
    }

    fun exibirRanking() {
        println("\n🏆 Ranking da Formação: $nome")
        val ranking = inscritos.sortedByDescending { it.pontuacaoTotal }
        ranking.forEachIndexed { index, usuario ->
            println("${index + 1}º - ${usuario.nome}: ${usuario.pontuacaoTotal} pts")
        }
    }
}
fun main() {
    // Criando conteúdos educacionais
    val kotlinBasico = ConteudoEducacional("Kotlin Básico", duracao = 90, pontuacao = 10)
    val pooKotlin = ConteudoEducacional("POO com Kotlin", duracao = 120, pontuacao = 20)
    val colecoes = ConteudoEducacional("Coleções Kotlin", duracao = 80, pontuacao = 15)
    val mentoriaKotlin = ConteudoEducacional("Mentoria: Dúvidas Kotlin", duracao = 60, pontuacao = 25)

    // Criando a formação
    val formacaoKotlinExpert = Formacao(
        nome = "Kotlin Expert Developer",
        nivel = Nivel.PLENO,
        conteudos = listOf(kotlinBasico, pooKotlin, colecoes, mentoriaKotlin)
    )

    // Criando alunos
    val riker = Usuario("Riker")
    val claudia = Usuario("Claudia")
    val thiago = Usuario("Thiago")
    val maira = Usuario("Maira")
    val felipe = Usuario("Felipe")

    // Matriculando alunos
    formacaoKotlinExpert.matricular(riker)
    formacaoKotlinExpert.matricular(claudia)
    formacaoKotlinExpert.matricular(thiago)
    formacaoKotlinExpert.matricular(maira)
    formacaoKotlinExpert.matricular(felipe)

    // Simulando progresso
    riker.progredir()
    riker.progredir()
    riker.progredir()
    riker.progredir()

    claudia.progredir()
    claudia.progredir()
    claudia.progredir()

    thiago.progredir()
    thiago.progredir()

    maira.progredir()

    // Exibindo progresso individual
    riker.exibirProgresso()
    claudia.exibirProgresso()
    thiago.exibirProgresso()
    maira.exibirProgresso()
    felipe.exibirProgresso() // ainda não progrediu

    // Exibindo ranking geral da formação
    formacaoKotlinExpert.exibirRanking()
}