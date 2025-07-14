from RPS import player
from RPS_game import play, quincy, kris, mrugesh, abbey

num_juegos = 1000

for bot in [quincy, kris, mrugesh, abbey]:
    win_rate = play(player, bot, num_juegos)  # Asumimos que play devuelve porcentaje de victorias (float)
    print(f"Resultados contra {bot.__name__}:")
    print(f"  Porcentaje de victoria: {win_rate:.2f}%\n")