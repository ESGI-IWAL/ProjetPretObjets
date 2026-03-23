import type { Object } from '@/types/object'

export const useActiveObject = () => useState<Object | null>('activeObject', () => null)