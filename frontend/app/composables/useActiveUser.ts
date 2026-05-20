import type { IUser } from '@/types/user'

export const useActiveUser = () => useState<IUser | null>('activeUser', () => null)