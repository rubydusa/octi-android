% suited for tuProlog

:- op(600, xfy, ':').

width(5).
height(6).
team(red).
team(green).
non_team(dummy).
next_team(red, green).
next_team(green, red).
winpos(red, [(1, 1), (2, 1), (3, 1), (4, 1)]).
winpos(green, [(1, 5), (2, 5), (3, 5), (4, 5)]).

select_1(X, [X | E], E).
select_1(X, [A | E0], [A | E1]) :-
    X \= A,
    select_1(X, E0, E1).

base_game(game(
              red,
              [
  red - 12,
                green - 12
              ],
              [
  octi(green, (1, 1), [(1, -1), (0, -1), (-1, -1), (-1, 0), (-1, 1), (0, 1), (1, 1), (1, 0)]), octi(green, (2, 1), []), octi(green, (3, 1), []), octi(green, (4, 1), []),
  octi(red, (1, 5), []), octi(red, (2, 5), []), octi(red, (3, 5), []), octi(red, (4, 5), [])
              ]
              )).

add_vectors((X0, Y0), (X1, Y1), (X, Y)) :-
    X is X0 + X1,
    Y is Y0 + Y1.

map_get([Key - Value | _], Key - Value).
map_get([Key0 - _| Rest], Key - Value) :-
    Key0 \= Key,
    map_get(Rest, Key - Value).

map_set([], [], _ - _).
map_set([Key - _ | Rest], [Key - Value | Cont], Key - Value) :-
    map_set(Rest, Cont, Key - Value).
map_set([Key0 - Value0 | Rest], [Key0 - Value0 | Cont], Key - Value) :-
    Key0 \= Key,
    map_set(Rest, Cont, Key - Value).

non_member(_, []).
non_member(E, [X | Xs]) :-
    E \= X,
    non_member(E, Xs).

valid_pos((X, Y)) :-
    width(W), height(H),
    between(0, W, X), between(0, H, Y).

valid_vector((1, 0)).
valid_vector((1, 1)).
valid_vector((0, 1)).
valid_vector((-1, 1)).
valid_vector((-1, 0)).
valid_vector((-1, -1)).
valid_vector((0, -1)).
valid_vector((1, -1)).

% place arrow
turn_1(
        game(Team0, Arrows0, Board0),
        game(Team1, Arrows1, Board1),
        move(place, (X, Y), (A, B))
     ) :-
    next_team(Team0, Team1),
    valid_pos((X, Y)),
    valid_vector((A, B)),

    % update arrow count
    map_get(Arrows0, Team0 - Team0Arrows),
    Team0Arrows > 0,
    NextTeam0Arrows is Team0Arrows - 1,
    map_set(Arrows0, Arrows1, Team0 - NextTeam0Arrows),

    % check octi doesn't already have arrow
    select_1(octi(Team0, (X, Y), Vectors), Board0, State0),
    non_member((A, B), Vectors),

    % update board
    NextVectors = [(A, B) | Vectors],
    Board1 = [octi(Team0, (X, Y), NextVectors) | State0].

% move octigon in case no blocking
turn_1(
        game(Team0, Arrows0, Board0),
        game(Team1, Arrows0, Board1),
        move(move, (X0, Y0), (X1, Y1))
     ) :-
    next_team(Team0, Team1),
    valid_pos((X0, Y0)), valid_pos((X1, Y1)),

    % get octi
    select_1(octi(Team0, (X0, Y0), Vectors), Board0, State0),
    member(NeededVector, Vectors),

    add_vectors(NeededVector, (X0, Y0), (X1, Y1)),

    % check that nothing is blocking
    non_member(octi(_, (X1, Y1), _), Board0),

    Board1 = [octi(Team0, (X1, Y1), Vectors) | State0].

% move octigon in case blocking but of own team (jump no eat)
turn_1(
        game(Team0, Arrows0, Board0),
        game(Team1, Arrows0, Board1),
        move(jump, (X0, Y0), (X1, Y1))
     ) :-
    next_team(Team0, Team1),
    valid_pos((X0, Y0)), valid_pos((X1, Y1)),

    % get octi
    select_1(octi(Team0, (X0, Y0), Vectors), Board0, State0),
    member(NeededVector, Vectors),

    % get blocking pos
    add_vectors(NeededVector, (X0, Y0), BlockingPos),
    add_vectors(BlockingPos, NeededVector, (X1, Y1)),

    member(JumpableTeam, [Team0, dummy]),
    % check that own team blocking and no octigon after jump
    member(octi(JumpableTeam, BlockingPos, _), Board0),
    non_member(octi(_, (X1, Y1), _), Board0),

    Board1 = [octi(Team0, (X1, Y1), Vectors) | State0].

% move octigon in case blocking but of other team (jump and eat)
turn_1(
        game(Team0, Arrows0, Board0),
        game(Team1, Arrows1, Board1),
        move(jump, (X0, Y0), (X1, Y1))
     ) :-
    next_team(Team0, Team1),
    valid_pos((X0, Y0)), valid_pos((X1, Y1)),

    % get octi
    select_1(octi(Team0, (X0, Y0), Vectors), Board0, State0),
    member(NeededVector, Vectors),

    % get blocking pos
    add_vectors(NeededVector, (X0, Y0), BlockingPos),
    add_vectors(BlockingPos, NeededVector, (X1, Y1)),

    % remove and get arrow count of octi at blocking pos
    select_1(octi(Team1, BlockingPos, OtherArrows), State0, State1),

    % update arrow count
    length(OtherArrows, AddedArrows),
    map_get(Arrows0, Team0 - Team0Arrows),
    NextTeam0Arrows is Team0Arrows + AddedArrows,
    map_set(Arrows0, Arrows1, Team0 - NextTeam0Arrows),

    % check that no octigon after jump
    non_member(octi(_, (X1, Y1), _), State1),

    % add dummy octigon
    Board1 = [octi(Team0, (X1, Y1), Vectors), octi(dummy, _, _) | State1].

% chain move 2
turn_1(
        game(Team0, Arrows0, Board0),
        game(Team1, Arrows1, Board1),
        move(chain, [(X0, Y0), (X1, Y1), (X2, Y2)])
     ) :-
    turn_1(
            game(Team0, Arrows0, Board0),
            game(Team1, Arrows0_1, Board0_1),
            move(jump, (X0, Y0), (X1, Y1))
        ),
    turn_1(
            game(Team0, Arrows0_1, Board0_1),
            game(Team1, Arrows1, Board1),
            move(jump, (X1, Y1), (X2, Y2))
        ).

% chain move N
turn_1(
        game(Team0, Arrows0, Board0),
        game(Team1, Arrows1, Board1),
        move(chain, [(X0, Y0), (X1, Y1), (X2, Y2) | Cont])
     ) :-
    turn_1(
            game(Team0, Arrows0, Board0),
            game(Team1, Arrows0_1, Board0_1),
            move(jump, (X0, Y0), (X1, Y1))
        ),
    turn_1(
            game(Team0, Arrows0_1, Board0_1),
            game(Team1, Arrows1, Board1),
            move(chain, [(X1, Y1), (X2, Y2) | Cont])
         ).

clean_octis([], []).
clean_octis([octi(Team, X, Y) | Rest0], [octi(Team, X, Y) | Rest]) :-
    team(Team),
    clean_octis(Rest0, Rest).
clean_octis([octi(Team, _, _) | Rest0], Rest) :-
    non_team(Team),
    clean_octis(Rest0, Rest).

clean(
         game(Team, Arrows, Board0),
         game(Team, Arrows, Board1)
     ) :-
    clean_octis(Board0, Board1).

% remove dummy octis
turn(Game0, Game, Move) :-
    turn_1(Game0, Game1, Move),
    clean(Game1, Game).

% for debugging purposes
play(Game, Game, []).

play(Game0, Game, [Move | Rest]) :-
    turn(Game0, Game1, Move),
    play(Game1, Game, Rest).

turn_step(Game0, Game1, move(Type, Target, Action), conclusive) :-
    turn(Game0, Game1, move(Type, Target, Action)).

turn_step(
           game(Team0, Arrows0, Board0),
           game(Team0, Arrows1, Board1),
           move(jump, Pos1, Pos2),
           inconclusive
        ) :-
    % jump is possible
    turn_1(
        game(Team0, Arrows0, Board0),
        game(Team1, Arrows1, Board1),
        move(jump, Pos1, Pos2)
    ),
    % jump is part of length-2 chain
    turn_1(
        game(Team0, Arrows0, Board0),
        game(Team1, _, _),
        move(chain, [Pos1, Pos2, _])
    ).

octis_team([octi(Team, _, _)], Team).
octis_team([octi(Team, _, _) | Xs], Team) :-
    octis_team(Xs, Team).

% team0 wins if all octis are of team0
win(game(_, _, Board), Team0) :-
    octis_team(Board, Team0).

% team wins if one of the octis are in winpos
win(game(_, _, Board), Team) :-
    winpos(Team, WinPos),
    member(Pos, WinPos),
    member(octi(Team, Pos, _), Board).

% team0 wins if team1 at their turn doesn't have any move
win(game(Team1, Arrows, Board), Team0) :-
    next_team(Team0, Team1),
    \+ turn_1(game(Team1, Arrows, Board), _, _).

json_map([], []).
json_map([A | As], [B | Bs]) :-
    json(A, B),
    json_map(As, Bs).

json((A, B), { x: A, y: B }).

json(
     move(chain, [(X1, Y1), (X2, Y2) | Cont]),
     {
     	type: chain,
        target: Target,
        action: Action,
        rest: ContJSON
     }) :-
    json((X1, Y1), Target),
    json((X2, Y2), Action),
    json_map(Cont, ContJSON).

json(
     move(Type, (X1, Y1), (X2, Y2)),
     {
     	type: Type,
        target: Target,
        action: Action,
        rest: []
     }) :-
    json((X1, Y1), Target),
    json((X2, Y2), Action).

json(Team - Arrows, { team: Team, arrows: Arrows }).

json(
     octi(Team, (X, Y), Arrows),
     {
     	team: Team,
        position: Position,
        arrows: ArrowsJSON
     }
    ) :-
    json((X, Y), Position),
    json_map(Arrows, ArrowsJSON).

json(
     game(Team, Arrows, Octis),
     {
     	team: Team,
        arrows: ArrowsJSON,
        octis: OctisJSON
     }) :-
    json_map(Arrows, ArrowsJSON),
    json_map(Octis, OctisJSON).